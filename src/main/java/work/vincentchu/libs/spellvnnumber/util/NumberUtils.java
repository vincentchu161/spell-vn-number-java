package work.vincentchu.libs.spellvnnumber.util;

import work.vincentchu.libs.spellvnnumber.SpellerConfig;
import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.error.InvalidNumberException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Utility class for number processing and validation
 * Lớp tiện ích cho việc xử lý và xác thực số
 */
public class NumberUtils {

    /**
     * Private constructor to prevent instantiation
     * Constructor riêng tư để ngăn việc khởi tạo
     */
    private NumberUtils() {
    }

    /**
     * Cleans and validates an input number
     * Làm sạch và xác thực một số đầu vào
     *
     * @param input  The input number to validate (Số đầu vào cần xác thực)
     * @param config The speller configuration (Cấu hình đọc số)
     * @return A valid string representation of the number (Biểu diễn chuỗi hợp lệ của số)
     * @throws InvalidFormatException if the input is null or invalid (nếu đầu vào là null hoặc không hợp lệ)
     * @throws InvalidNumberException if the number format is invalid (nếu định dạng số không hợp lệ)
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
            str = normalizeNumberString(str, config);
            return toPlainString(str, config.getDecimalPoint());
        }

        if (input instanceof Number) {
            return toPlainString((Number) input);
        }

        throw new InvalidFormatException("Unsupported input type");
    }

    /**
     * Converts a Number to its full string representation without using scientific notation (E).
     * Assumes input is not null.
     * Optimized to avoid unnecessary BigDecimal creation for types like Integer, Long, etc.
     * <p>
     * Chuyển đổi một Number thành biểu diễn chuỗi đầy đủ mà không sử dụng ký hiệu khoa học (E).
     * Giả định đầu vào không phải null.
     * Được tối ưu hóa để tránh tạo không cần thiết BigDecimal cho các kiểu như Integer, Long, v.v.
     *
     * @param number The number to convert (Số cần chuyển đổi)
     * @return The plain string representation (Biểu diễn chuỗi thuần túy)
     */
    public static String toPlainString(Number number) {
        // BigDecimal: use its own toPlainString then trim if needed
        if (number instanceof BigDecimal) {
            return stripTrailingDecimal(((BigDecimal) number).stripTrailingZeros().toPlainString());
        }

        // Direct types: no risk of scientific notation or precision loss
        if (number instanceof BigInteger
            || number instanceof Integer
            || number instanceof Long
            || number instanceof Short
            || number instanceof Byte) {
            return number.toString();
        }

        // Double/Float or unknown Number subtype
        // Use string constructor to avoid precision issues in BigDecimal
        BigDecimal decimal = new BigDecimal(number.toString());
        return stripTrailingDecimal(decimal.stripTrailingZeros().toPlainString());
    }

    /**
     * Removes unnecessary ".0" suffix from plain decimal strings.
     * E.g., "1.0" → "1", "2.50" → "2.50", "3.000" → "3", "4.01" → "4.01"
     */
    private static String stripTrailingDecimal(String value) {
        if (value.contains(".") && value.matches("[-]?\\d+\\.0+")) {
            return value.substring(0, value.indexOf('.'));
        }
        return value;
    }

    /**
     * Converts a localized numeric string (with custom decimal separator) to plain string format.
     * <p>
     * Assumes:
     * - Input is non-null and properly formatted.
     * - Only one type of decimal separator is used (either '.' or ',').
     *
     * @param numberStr        the input string using custom decimal separator (e.g., "123,45")
     * @param decimalSeparator the decimal separator used in the input (e.g., ',' or '.')
     * @return plain string representation (e.g., "123,45" or "123.45") with preserved minus for "-0"
     */
    public static String toPlainString(String numberStr, String decimalSeparator) {
        // Check if the decimal separator is not '.'
        boolean isCommaSeparator = decimalSeparator != null && !decimalSeparator.equals(".");

        // Step 1: Check if the input starts with a negative sign
        boolean hasLeadingNegative = numberStr.charAt(0) == '-';

        // Step 2: Normalize input by replacing the decimal separator (if needed)
        String normalized = isCommaSeparator ? numberStr.replace(',', '.') : numberStr;

        // Step 3: Convert to BigDecimal and get plain string
        BigDecimal bd = new BigDecimal(normalized);
        String plain = bd.toPlainString();

        // Step 4: If original string had a negative sign and value is zero, re-attach the negative sign
        if (hasLeadingNegative && bd.compareTo(BigDecimal.ZERO) == 0) {
            plain = "-" + plain;
        }

        // Step 5: Convert '.' to ',' only when decimalSeparator is not '.'
        return isCommaSeparator ? plain.replace('.', ',') : plain;
    }

    /**
     * Repeats a string a specified number of times
     * Lặp lại một chuỗi một số lần xác định
     *
     * @param str   The string to repeat (Chuỗi cần lặp lại)
     * @param count The number of times to repeat (Số lần lặp lại)
     * @return The repeated string (Chuỗi đã được lặp lại)
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
     * Chuẩn hóa một chuỗi số bằng cách làm sạch khoảng trắng và ký tự đặc biệt
     *
     * @param input  The string to normalize (Chuỗi cần chuẩn hóa)
     * @param config The speller configuration (Cấu hình đọc số)
     * @return Normalized number string (Chuỗi số đã chuẩn hóa)
     * @throws InvalidNumberException if the number format is invalid (nếu định dạng số không hợp lệ)
     */
    public static String normalizeNumberString(String input, SpellerConfig config) {
        // Remove whitespace and replace special characters
        // Xóa khoảng trắng và thay thế các ký tự đặc biệt
        String normalized = input.replaceAll("[\\s\\u00A0]", "")
                .replaceAll("[\\u2013\\u2014]", "-")
                .replace(config.getThousandSign(), "");

        // Create a regex pattern for valid numbers with the configured decimal point
        // Tạo một mẫu regex cho các số hợp lệ với dấu thập phân được cấu hình
        String decimalPoint = Pattern.quote(config.getDecimalPoint());
        Pattern validNumberPattern = Pattern.compile("^-?\\d+(" + decimalPoint + "\\d+)?$");

        if (!validNumberPattern.matcher(normalized).matches()) {
            throw new InvalidNumberException("Invalid number format");
        }

        return normalized;
    }

    /**
     * Removes leading characters from a string
     * Xóa các ký tự đứng đầu khỏi một chuỗi
     *
     * @param str        The input string (Chuỗi đầu vào)
     * @param charToTrim The character to trim (Ký tự cần cắt bỏ)
     * @return The string with leading chars removed (Chuỗi với các ký tự đứng đầu đã bị xóa)
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
     * Xóa các số 0 ở cuối chuỗi
     *
     * @param str                     The input string (Chuỗi đầu vào)
     * @param charToTrim              The character to trim (Ký tự cần cắt bỏ)
     * @param keepOneZeroWhenAllZeros Whether to keep one zero when all are zeros
     *                                (Có giữ lại một số 0 khi tất cả đều là số 0 hay không)
     * @return The string with trailing chars removed (Chuỗi với các ký tự cuối đã bị xóa)
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
     * Cắt bỏ các số 0 thừa ở đầu và cuối chuỗi số
     *
     * @param config    The speller configuration (Cấu hình đọc số)
     * @param numberStr The number string to process (Chuỗi số cần xử lý)
     * @return The trimmed number string (Chuỗi số đã được cắt bỏ)
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