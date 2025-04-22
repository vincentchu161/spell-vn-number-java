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
 * Lớp cấu hình cho việc đọc số thành chữ
 */
public class SpellerConfig {
    /**
     * Map of digit names
     * Ánh xạ tên các chữ số
     */
    private final Map<String, String> digitNames = new HashMap<>();

    /**
     * Map of unit names
     * Ánh xạ tên các đơn vị
     */
    private final Map<Integer, String> unitNames = new HashMap<>();

    /**
     * Specific text for special cases
     * Văn bản đặc biệt cho các trường hợp đặc biệt
     */
    private final SpecificText specificText = new SpecificText();

    /**
     * Separator between spelled parts
     * Dấu phân cách giữa các phần đã đọc
     */
    private String separator = " ";

    /**
     * Sign for negative numbers
     * Dấu cho số âm
     */
    private String negativeSign = "-";

    /**
     * Decimal point character
     * Ký tự dấu thập phân
     */
    private String decimalPoint = ".";

    /**
     * Thousand separator
     * Dấu phân cách hàng nghìn
     */
    private String thousandSign = ",";

    /**
     * Text representation of negative
     * Biểu diễn văn bản của số âm
     */
    private String negativeText = "âm";

    /**
     * Text representation of decimal point
     * Biểu diễn văn bản của dấu thập phân
     */
    private String pointText = "chấm";

    /**
     * Whether to capitalize the first letter
     * Có viết hoa chữ cái đầu tiên hay không
     */
    private boolean capitalizeInitial = true;

    /**
     * Currency unit to append at the end
     * Đơn vị tiền tệ để thêm vào cuối
     */
    private String currencyUnit = "";

    /**
     * Character representing redundant zero
     * Ký tự biểu diễn số 0 thừa
     */
    private String redundantZeroChar = "0";

    /**
     * Whether to keep one zero when all digits are zeros
     * Có giữ lại một số 0 khi tất cả các chữ số đều là 0 hay không
     */
    private boolean keepOneZeroWhenAllZeros = false;

    /**
     * Creates a new speller configuration with default values
     * Tạo một cấu hình đọc số mới với các giá trị mặc định
     */
    public SpellerConfig() {
        initializeDefaultConfig();
    }

    /**
     * Creates a new speller configuration by copying an existing one
     * Tạo một cấu hình đọc số mới bằng cách sao chép từ một cấu hình đã tồn tại
     *
     * @param config The configuration to copy from (Cấu hình để sao chép)
     */
    public SpellerConfig(SpellerConfig config) {
        initializeDefaultConfig();
        if (config != null) {
            // Copy basic properties
            // Sao chép các thuộc tính cơ bản
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
            // Sao chép tên các chữ số
            this.digitNames.clear();
            this.digitNames.putAll(config.digitNames);

            // Copy unit names
            // Sao chép tên các đơn vị
            this.unitNames.clear();
            this.unitNames.putAll(config.unitNames);

            // Copy specific text
            // Sao chép văn bản đặc biệt
            this.specificText.setOddText(config.specificText.getOddText());
            this.specificText.setTenText(config.specificText.getTenText());
            this.specificText.setOneToneText(config.specificText.getOneToneText());
            this.specificText.setFourToneText(config.specificText.getFourToneText());
            this.specificText.setFiveToneText(config.specificText.getFiveToneText());
        }
    }

    /**
     * Initialize the default configuration values
     * Khởi tạo các giá trị cấu hình mặc định
     */
    private void initializeDefaultConfig() {
        // Initialize digit names
        // Khởi tạo tên các chữ số
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
        // Khởi tạo tên các đơn vị
        unitNames.put(Index.BILLION.value(), "tỷ");
        unitNames.put(Index.MILLION.value(), "triệu");
        unitNames.put(Index.THOUSAND.value(), "nghìn");
        unitNames.put(Index.HUNDREDS.value(), "trăm");
        unitNames.put(Index.TENS.value(), "mươi");
        unitNames.put(Index.UNITS.value(), "");
    }

    // Getters and setters - Các phương thức getter và setter

    /**
     * Gets the separator between spelled parts
     * Lấy dấu phân cách giữa các phần đã đọc
     * @return the separator string
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Sets the separator between spelled parts
     * Đặt dấu phân cách giữa các phần đã đọc
     * @param separator the separator string to set
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Gets the sign for negative numbers
     * Lấy dấu cho số âm
     * @return the negative sign string
     */
    public String getNegativeSign() {
        return negativeSign;
    }

    /**
     * Sets the sign for negative numbers
     * Đặt dấu cho số âm
     * @param negativeSign the negative sign string to set
     */
    public void setNegativeSign(String negativeSign) {
        this.negativeSign = negativeSign;
    }

    /**
     * Gets the decimal point character
     * Lấy ký tự dấu thập phân
     * @return the decimal point string
     */
    public String getDecimalPoint() {
        return decimalPoint;
    }

    /**
     * Sets the decimal point character
     * Đặt ký tự dấu thập phân
     * @param decimalPoint the decimal point string to set
     */
    public void setDecimalPoint(String decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    /**
     * Gets the thousand separator
     * Lấy dấu phân cách hàng nghìn
     * @return the thousand separator string
     */
    public String getThousandSign() {
        return thousandSign;
    }

    /**
     * Sets the thousand separator
     * Đặt dấu phân cách hàng nghìn
     * @param thousandSign the thousand separator string to set
     */
    public void setThousandSign(String thousandSign) {
        this.thousandSign = thousandSign;
    }

    /**
     * Gets the text representation of negative
     * Lấy biểu diễn văn bản của số âm
     * @return the negative text string
     */
    public String getNegativeText() {
        return negativeText;
    }

    /**
     * Sets the text representation of negative
     * Đặt biểu diễn văn bản của số âm
     * @param negativeText the negative text string to set
     */
    public void setNegativeText(String negativeText) {
        this.negativeText = negativeText;
    }

    /**
     * Gets the text representation of decimal point
     * Lấy biểu diễn văn bản của dấu thập phân
     * @return the point text string
     */
    public String getPointText() {
        return pointText;
    }

    /**
     * Sets the text representation of decimal point
     * Đặt biểu diễn văn bản của dấu thập phân
     * @param pointText the point text string to set
     */
    public void setPointText(String pointText) {
        this.pointText = pointText;
    }

    /**
     * Checks if the first letter should be capitalized
     * Kiểm tra xem chữ cái đầu tiên có nên được viết hoa không
     * @return true if first letter should be capitalized, false otherwise
     */
    public boolean isCapitalizeInitial() {
        return capitalizeInitial;
    }

    /**
     * Sets whether the first letter should be capitalized
     * Đặt xem chữ cái đầu tiên có nên được viết hoa không
     * @param capitalizeInitial true to capitalize first letter, false otherwise
     */
    public void setCapitalizeInitial(boolean capitalizeInitial) {
        this.capitalizeInitial = capitalizeInitial;
    }

    /**
     * Gets the currency unit
     * Lấy đơn vị tiền tệ
     * @return the currency unit string
     */
    public String getCurrencyUnit() {
        return currencyUnit;
    }

    /**
     * Sets the currency unit
     * Đặt đơn vị tiền tệ
     * @param currencyUnit the currency unit string to set
     */
    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    /**
     * Gets the character representing redundant zero
     * Lấy ký tự biểu diễn số 0 thừa
     * @return the redundant zero character string
     */
    public String getRedundantZeroChar() {
        return redundantZeroChar;
    }

    /**
     * Sets the character representing redundant zero
     * Đặt ký tự biểu diễn số 0 thừa
     * @param redundantZeroChar the redundant zero character string to set
     */
    public void setRedundantZeroChar(String redundantZeroChar) {
        this.redundantZeroChar = redundantZeroChar;
    }

    /**
     * Checks if one zero should be kept when all digits are zeros
     * Kiểm tra xem có nên giữ lại một số 0 khi tất cả các chữ số đều là 0 hay không
     * @return true if one zero should be kept, false otherwise
     */
    public boolean isKeepOneZeroWhenAllZeros() {
        return keepOneZeroWhenAllZeros;
    }

    /**
     * Sets whether one zero should be kept when all digits are zeros
     * Đặt xem có nên giữ lại một số 0 khi tất cả các chữ số đều là 0 hay không
     * @param keepOneZeroWhenAllZeros true to keep one zero, false otherwise
     */
    public void setKeepOneZeroWhenAllZeros(boolean keepOneZeroWhenAllZeros) {
        this.keepOneZeroWhenAllZeros = keepOneZeroWhenAllZeros;
    }

    /**
     * Gets the name of a digit
     * Lấy tên của một chữ số
     *
     * @param digit The digit to get the name for
     * @return The name of the digit
     */
    public String getDigitName(String digit) {
        return digitNames.get(digit);
    }

    /**
     * Sets the name of a digit
     * Đặt tên cho một chữ số
     *
     * @param digit The digit to set the name for
     * @param name  The name to set
     */
    public void setDigitName(String digit, String name) {
        digitNames.put(digit, name);
    }

    /**
     * Gets the name of a unit by index
     * Lấy tên của một đơn vị theo chỉ số
     *
     * @param index The index of the unit
     * @return The name of the unit
     */
    public String getUnitName(int index) {
        return unitNames.get(index);
    }

    /**
     * Sets the name of a unit by index
     * Đặt tên cho một đơn vị theo chỉ số
     *
     * @param index The index of the unit
     * @param name  The name to set
     */
    public void setUnitName(int index, String name) {
        unitNames.put(index, name);
    }

    /**
     * Gets the unit name of a magnitude index
     * Lấy tên đơn vị của một chỉ số cỡ
     *
     * @param magnitudeIndex The magnitude index
     * @return The unit name of the magnitude
     */
    public String getUnitNameOfMagnitude(int magnitudeIndex) {
        if (magnitudeIndex == Index.BILLION.value()) {
            return unitNames.get(Index.MILLION.value());
        } else if (magnitudeIndex == Index.MILLION.value()) {
            return unitNames.get(Index.THOUSAND.value());
        }
        return unitNames.get(Index.UNITS.value());
    }

    /**
     * Gets the specific text object
     * Lấy đối tượng văn bản đặc biệt
     * @return the specific text object
     */
    public SpecificText getSpecificText() {
        return specificText;
    }

    /**
     * Gets the text for odd positions
     * Lấy văn bản cho các vị trí lẻ
     * @return the text for odd positions
     */
    public String getOddText() {
        return specificText.getOddText();
    }

    /**
     * Gets the text for tens
     * Lấy văn bản cho hàng chục
     * @return the text for tens
     */
    public String getTenText() {
        return specificText.getTenText();
    }

    /**
     * Gets the special tone text for one
     * Lấy văn bản âm điệu đặc biệt cho số một
     * @return the special tone text for one
     */
    public String getOneToneText() {
        return specificText.getOneToneText();
    }

    /**
     * Gets the special tone text for four
     * Lấy văn bản âm điệu đặc biệt cho số bốn
     * @return the special tone text for four
     */
    public String getFourToneText() {
        return specificText.getFourToneText();
    }

    /**
     * Gets the special tone text for five
     * Lấy văn bản âm điệu đặc biệt cho số năm
     * @return the special tone text for five
     */
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
     * <p>
     * Phân tích và xử lý số đầu vào thành định dạng có cấu trúc.
     * Phương thức này chịu trách nhiệm:
     * - Làm sạch và xác thực số đầu vào
     * - Xử lý dấu âm
     * - Tách số thành phần nguyên và phần thập phân
     * - Cắt bỏ các số 0 thừa theo cấu hình
     *
     * @param input The number to parse (Số cần phân tích)
     * @return A NumberData object containing the parsed number information
     * (Đối tượng NumberData chứa thông tin số đã phân tích)
     */
    public INumberData parseNumberData(Object input) {
        // Clean and validate input
        // Làm sạch và xác thực đầu vào
        String numberStr = NumberUtils.cleanInputNumber(input, this);

        // Handle negative sign
        // Xử lý dấu âm
        boolean isNegative = numberStr.startsWith(negativeSign);
        if (isNegative) {
            numberStr = numberStr.substring(negativeSign.length());
        }

        // Trim redundant zeros && Split into integral and fractional parts
        // Cắt bỏ các số 0 thừa && Tách thành phần nguyên và phần thập phân
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