package work.vincentchu.libs.spellvnnumber;

import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.type.Index;
import work.vincentchu.libs.spellvnnumber.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Main class for spelling numbers in Vietnamese
 */
public class Speller {
    private static final int NUMBER_OF_GROUPS = 3;
    private static final int NUMBER_OF_POSITIONS = 3;

    private final SpellerConfig config;

    public Speller() {
        this.config = new SpellerConfig();
    }

    public Speller(SpellerConfig config) {
        this.config = new SpellerConfig(config);
    }

    /**
     * Spells a number in Vietnamese
     *
     * @param input The number to spell
     * @return The spelled number in Vietnamese
     */
    public String spell(Object input) {
        if (input == null) {
            throw new InvalidFormatException("Input cannot be null");
        }

        String numberStr = NumberUtils.cleanInputNumber(input, config);
        numberStr = NumberUtils.trimRedundantZeros(config, numberStr);

        boolean isNegative = numberStr.startsWith(config.getNegativeSign());
        if (isNegative) {
            numberStr = numberStr.substring(1);
        }

        String[] parts = numberStr.split(Pattern.quote(config.getDecimalPoint()));
        String integralPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "";

        List<String> spelledParts = new ArrayList<>();

        if (isNegative) {
            spelledParts.add(config.getNegativeText());
        }

        processPart(spelledParts, integralPart);

        if (!fractionalPart.isEmpty()) {
            spelledParts.add(config.getPointText());
            processPart(spelledParts, fractionalPart);
        }

        if (config.isCapitalizeInitial() && !spelledParts.isEmpty()) {
            String firstPart = spelledParts.get(0);
            spelledParts.set(0, firstPart.substring(0, 1).toUpperCase() + firstPart.substring(1));
        }

        String result = String.join(config.getSeparator(), spelledParts);

        if (!config.getCurrencyUnit().isEmpty()) {
            result += " " + config.getCurrencyUnit();
        }

        return result;
    }

    private void processPart(List<String> spelledParts, String numberStr) {
        if (numberStr.isEmpty()) {
            return;
        }

        int mod = numberStr.length() % NUMBER_OF_POSITIONS;
        String paddedNumb = mod != 0 ? NumberUtils.repeatString("0", NUMBER_OF_POSITIONS - mod) + numberStr : numberStr;

        int totalThreeDigitSegments = paddedNumb.length() / NUMBER_OF_POSITIONS;

        int magnitudeMod = totalThreeDigitSegments % NUMBER_OF_GROUPS;
        int remainingGroups = magnitudeMod == 0
                ? totalThreeDigitSegments / NUMBER_OF_GROUPS
                : (totalThreeDigitSegments / NUMBER_OF_GROUPS) + 1;

        int currentMagnitudeIndex = magnitudeMod != 0 ? NUMBER_OF_GROUPS - magnitudeMod : Index.BILLION.value();

        boolean isFirst = true;
        int i = 0;

        while (remainingGroups > 0) {
            if (!isFirst) {
                spelledParts.add(config.getUnitName(currentMagnitudeIndex));
            }

            for (; currentMagnitudeIndex <= Index.THOUSAND.value(); currentMagnitudeIndex++) {
                char hundredsDigit = paddedNumb.charAt(i++);
                char tensDigit = paddedNumb.charAt(i++);
                char unitsDigit = paddedNumb.charAt(i++);

                if (isFirst) {
                    if (hundredsDigit != '0') {
                        isFirst = false;
                        spellHundreds(spelledParts, hundredsDigit, tensDigit, unitsDigit);
                    }
                    if (!isFirst || tensDigit != '0') {
                        isFirst = false;
                        spellTens(spelledParts, tensDigit, unitsDigit);
                    }
                    if (!isFirst || unitsDigit != '0') {
                        isFirst = false;
                        spellUnits(spelledParts, hundredsDigit, tensDigit, unitsDigit, currentMagnitudeIndex);
                    }
                    if (isFirst) {
                        isFirst = false;
                        spelledParts.add(config.getDigitName("0"));
                    }
                } else {
                    spellHundreds(spelledParts, hundredsDigit, tensDigit, unitsDigit);
                    spellTens(spelledParts, tensDigit, unitsDigit);
                    spellUnits(spelledParts, hundredsDigit, tensDigit, unitsDigit, currentMagnitudeIndex);
                }
            }

            remainingGroups--;
            currentMagnitudeIndex = Index.BILLION.value();
        }
    }

    private void spellUnits(List<String> spelledParts, char hundredsDigit, char tensDigit, char unitsDigit, int currentMagnitudeIndex) {
        if (unitsDigit == '0') {
            if (tensDigit != '0' || hundredsDigit != '0') {
                if (currentMagnitudeIndex != Index.THOUSAND.value()) {
                    spelledParts.add(config.getUnitNameOfMagnitude(currentMagnitudeIndex));
                }
            }
            return;
        }

        if (unitsDigit == '1') {
            if (tensDigit != '0' && tensDigit != '1') {
                spelledParts.add(config.getSpecificText().getOneToneText());
            } else {
                spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
            }
        } else if (unitsDigit == '4') {
            if (tensDigit != '0' && tensDigit != '1') {
                spelledParts.add(config.getSpecificText().getFourToneText());
            } else {
                spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
            }
        } else if (unitsDigit == '5') {
            if (tensDigit != '0') {
                spelledParts.add(config.getSpecificText().getFiveToneText());
            } else {
                spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
            }
        } else {
            spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
        }

        if (currentMagnitudeIndex != Index.THOUSAND.value()) {
            spelledParts.add(config.getUnitNameOfMagnitude(currentMagnitudeIndex));
        }
    }

    private void spellTens(List<String> spelledParts, char tensDigit, char unitsDigit) {
        if (tensDigit == '0') {
            if (unitsDigit != '0') {
                spelledParts.add(config.getSpecificText().getOddText());
            }
        } else if (tensDigit == '1') {
            spelledParts.add(config.getSpecificText().getTenText());
        } else {
            spelledParts.add(config.getDigitName(String.valueOf(tensDigit)));
            spelledParts.add(config.getUnitName(Index.TENS.value()));
        }
    }

    private void spellHundreds(List<String> spelledParts, char hundredsDigit, char tensDigit, char unitsDigit) {
        if (hundredsDigit == '0') {
            if (!(tensDigit == '0' && unitsDigit == '0')) {
                spelledParts.add(config.getDigitName(String.valueOf(hundredsDigit)));
                spelledParts.add(config.getUnitName(Index.HUNDREDS.value()));
            }
        } else {
            spelledParts.add(config.getDigitName(String.valueOf(hundredsDigit)));
            spelledParts.add(config.getUnitName(Index.HUNDREDS.value()));
        }
    }
} 