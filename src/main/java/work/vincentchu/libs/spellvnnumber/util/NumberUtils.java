package work.vincentchu.libs.spellvnnumber.util;

import work.vincentchu.libs.spellvnnumber.SpellerConfig;
import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.error.InvalidNumberException;

import java.util.regex.Pattern;

/**
 * Utility class for number processing and validation
 */
public class NumberUtils {
    private static final Pattern VALID_NUMBER_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");
    private NumberUtils() {
    }

    /**
     * Cleans and validates an input number
     *
     * @param input  The input number to validate
     * @param config The speller configuration
     * @return A valid string representation of the number
     * @throws InvalidFormatException if the input is null or invalid
     * @throws InvalidNumberException if the number format is invalid
     */
    public static String cleanInputNumber(Object input, SpellerConfig config) {
        if (input == null) {
            throw new InvalidFormatException("Input cannot be null");
        }

        if (input instanceof String) {
            String str = (String) input;
            if (str.isEmpty()) {
                throw new InvalidFormatException("Input cannot be empty");
            }
            return normalizeNumberString(str, config);
        }

        if (input instanceof Number) {
            if (input instanceof Double || input instanceof Float) {
                double value = ((Number) input).doubleValue();
                if (Double.isInfinite(value) || Double.isNaN(value)) {
                    throw new InvalidFormatException("Input must be a finite number");
                }
                return convertScientificToDecimal(value);
            }
            return input.toString();
        }

        throw new InvalidFormatException("Unsupported input type");
    }

    /**
     * Converts a scientific notation number to decimal string
     *
     * @param number The number to convert
     * @return The decimal string representation
     */
    public static String convertScientificToDecimal(double number) {
        return String.format("%.0f", number);
    }

    /**
     * Repeats a string a specified number of times
     *
     * @param str   The string to repeat
     * @param count The number of times to repeat
     * @return The repeated string
     */
    public static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Normalizes a number string by cleaning whitespace and special characters
     *
     * @param input  The string to normalize
     * @param config The speller configuration
     * @return Normalized number string
     */
    public static String normalizeNumberString(String input, SpellerConfig config) {
        String normalized = input.replaceAll("[\\s\\u00A0]", "")
                .replaceAll("[\\u2013\\u2014]", "-")
                .replace(config.getThousandSign(), "");

        String decimalPoint = Pattern.quote(config.getDecimalPoint());
        Pattern validNumberPattern = Pattern.compile("^-?\\d+(" + decimalPoint + "\\d+)?$");
        
        if (!validNumberPattern.matcher(normalized).matches()) {
            throw new InvalidNumberException("Invalid number format");
        }

        return normalized;
    }

    /**
     * Removes leading characters from a string
     *
     * @param str        The input string
     * @param charToTrim The character to trim
     * @return The string with leading chars removed
     */
    public static String trimLeft(String str, String charToTrim) {
        int startIndex = 0;
        while (startIndex < str.length() && str.charAt(startIndex) == charToTrim.charAt(0)) {
            startIndex++;
        }
        return startIndex == str.length() ? charToTrim : str.substring(startIndex);
    }

    /**
     * Removes trailing zeros from a string
     *
     * @param str                     The input string
     * @param charToTrim              The character to trim
     * @param keepOneZeroWhenAllZeros Whether to keep one zero when all are zeros
     * @return The string with trailing chars removed
     */
    public static String trimRight(String str, String charToTrim, boolean keepOneZeroWhenAllZeros) {
        int endIndex = str.length() - 1;
        while (endIndex >= 0 && str.charAt(endIndex) == charToTrim.charAt(0)) {
            endIndex--;
        }
        if (endIndex < 0) {
            if (keepOneZeroWhenAllZeros) return charToTrim;
            return "";
        } else {
            return str.substring(0, endIndex + 1);
        }
    }

    /**
     * Trims redundant zeros at the beginning and end of a number string
     *
     * @param config    The speller configuration
     * @param numberStr The number string to process
     * @return The trimmed number string
     */
    public static String trimRedundantZeros(SpellerConfig config, String numberStr) {
        if (numberStr.contains(config.getDecimalPoint())) {
            String[] parts = numberStr.split(Pattern.quote(config.getDecimalPoint()));
            String intPart = trimLeft(parts[0], config.getRedundantZeroChar());
            String fractionalPart = trimRight(parts[1], config.getRedundantZeroChar(), config.isKeepOneZeroWhenAllZeros());
            return intPart + config.getDecimalPoint() + fractionalPart;
        } else {
            return trimLeft(numberStr, config.getRedundantZeroChar());
        }
    }
} 