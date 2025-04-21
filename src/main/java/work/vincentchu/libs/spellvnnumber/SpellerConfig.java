package work.vincentchu.libs.spellvnnumber;

import work.vincentchu.libs.spellvnnumber.type.INumberData;
import work.vincentchu.libs.spellvnnumber.type.Index;
import work.vincentchu.libs.spellvnnumber.type.NumberData;
import work.vincentchu.libs.spellvnnumber.type.SpecificText;
import work.vincentchu.libs.spellvnnumber.util.NumberUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for number spelling
 */
public class SpellerConfig {
    private final Map<String, String> digitNames = new HashMap<>();
    private final Map<Integer, String> unitNames = new HashMap<>();
    private final SpecificText specificText = new SpecificText();

    private String separator = " ";
    private String negativeSign = "-";
    private String decimalPoint = ".";
    private String thousandSign = ",";
    private String negativeText = "âm";
    private String pointText = "chấm";
    private boolean capitalizeInitial = true;
    private String currencyUnit = "";
    private String redundantZeroChar = "0";
    private boolean keepOneZeroWhenAllZeros = false;

    public SpellerConfig() {
        initializeDefaultConfig();
    }

    public SpellerConfig(SpellerConfig config) {
        initializeDefaultConfig();
        if (config != null) {
            // Copy basic properties
            this.separator = config.separator;
            this.negativeSign = config.negativeSign;
            this.decimalPoint = config.decimalPoint;
            this.thousandSign = config.thousandSign;
            this.negativeText = config.negativeText;
            this.pointText = config.pointText;
            this.capitalizeInitial = config.capitalizeInitial;
            this.currencyUnit = config.currencyUnit;
            this.redundantZeroChar = config.redundantZeroChar;
            this.keepOneZeroWhenAllZeros = config.keepOneZeroWhenAllZeros;

            // Copy digit names
            this.digitNames.clear();
            this.digitNames.putAll(config.digitNames);

            // Copy unit names
            this.unitNames.clear();
            this.unitNames.putAll(config.unitNames);

            // Copy specific text
            this.specificText.setOddText(config.specificText.getOddText());
            this.specificText.setTenText(config.specificText.getTenText());
            this.specificText.setOneToneText(config.specificText.getOneToneText());
            this.specificText.setFourToneText(config.specificText.getFourToneText());
            this.specificText.setFiveToneText(config.specificText.getFiveToneText());
        }
    }

    private void initializeDefaultConfig() {
        // Initialize digit names
        digitNames.put("0", "không");
        digitNames.put("1", "một");
        digitNames.put("2", "hai");
        digitNames.put("3", "ba");
        digitNames.put("4", "bốn");
        digitNames.put("5", "năm");
        digitNames.put("6", "sáu");
        digitNames.put("7", "bảy");
        digitNames.put("8", "tám");
        digitNames.put("9", "chín");

        // Initialize unit names
        unitNames.put(Index.BILLION.value(), "tỷ");
        unitNames.put(Index.MILLION.value(), "triệu");
        unitNames.put(Index.THOUSAND.value(), "nghìn");
        unitNames.put(Index.HUNDREDS.value(), "trăm");
        unitNames.put(Index.TENS.value(), "mươi");
        unitNames.put(Index.UNITS.value(), "");
    }

    // Getters and setters
    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getNegativeSign() {
        return negativeSign;
    }

    public void setNegativeSign(String negativeSign) {
        this.negativeSign = negativeSign;
    }

    public String getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(String decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    public String getThousandSign() {
        return thousandSign;
    }

    public void setThousandSign(String thousandSign) {
        this.thousandSign = thousandSign;
    }

    public String getNegativeText() {
        return negativeText;
    }

    public void setNegativeText(String negativeText) {
        this.negativeText = negativeText;
    }

    public String getPointText() {
        return pointText;
    }

    public void setPointText(String pointText) {
        this.pointText = pointText;
    }

    public boolean isCapitalizeInitial() {
        return capitalizeInitial;
    }

    public void setCapitalizeInitial(boolean capitalizeInitial) {
        this.capitalizeInitial = capitalizeInitial;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getRedundantZeroChar() {
        return redundantZeroChar;
    }

    public void setRedundantZeroChar(String redundantZeroChar) {
        this.redundantZeroChar = redundantZeroChar;
    }

    public boolean isKeepOneZeroWhenAllZeros() {
        return keepOneZeroWhenAllZeros;
    }

    public void setKeepOneZeroWhenAllZeros(boolean keepOneZeroWhenAllZeros) {
        this.keepOneZeroWhenAllZeros = keepOneZeroWhenAllZeros;
    }

    public String getDigitName(String digit) {
        return digitNames.get(digit);
    }

    public void setDigitName(String digit, String name) {
        digitNames.put(digit, name);
    }

    public String getUnitName(int index) {
        return unitNames.get(index);
    }

    public void setUnitName(int index, String name) {
        unitNames.put(index, name);
    }

    public String getUnitNameOfMagnitude(int magnitudeIndex) {
        if (magnitudeIndex == Index.BILLION.value()) {
            return unitNames.get(Index.MILLION.value());
        } else if (magnitudeIndex == Index.MILLION.value()) {
            return unitNames.get(Index.THOUSAND.value());
        }
        return unitNames.get(Index.UNITS.value());
    }

    public SpecificText getSpecificText() {
        return specificText;
    }

    public String getOddText() {
        return specificText.getOddText();
    }

    public String getTenText() {
        return specificText.getTenText();
    }

    public String getOneToneText() {
        return specificText.getOneToneText();
    }

    public String getFourToneText() {
        return specificText.getFourToneText();
    }

    public String getFiveToneText() {
        return specificText.getFiveToneText();
    }

    /**
     * Parses and processes the input number into a structured format.
     * This method is responsible for:
     * - Cleaning and validating the input number
     * - Handling negative signs
     * - Splitting the number into integral and fractional parts
     * - Trimming redundant zeros according to configuration
     *
     * @param input The number to parse
     * @return A NumberData object containing the parsed number information
     */
    public INumberData parseNumberData(Object input) {
        // Clean and validate input
        String numberStr = NumberUtils.cleanInputNumber(input, this);

        // Handle negative sign
        boolean isNegative = numberStr.startsWith(negativeSign);
        if (isNegative) {
            numberStr = numberStr.substring(negativeSign.length());
        }

        // Trim redundant zeros && Split into integral and fractional parts
        int pointPos = numberStr.indexOf(decimalPoint);
        if (pointPos == -1) {
            numberStr = NumberUtils.trimLeft(numberStr, redundantZeroChar);
            return new NumberData(isNegative, numberStr, "");
        } else {
            return new NumberData(
                    isNegative,
                    NumberUtils.trimLeft(numberStr.substring(0, pointPos), redundantZeroChar),
                    NumberUtils.trimRight(numberStr.substring(pointPos + 1), redundantZeroChar, keepOneZeroWhenAllZeros)
            );
        }
    }
} 