package work.vincentchu.libs.spellvnnumber.type;

/**
 * Implementation of NumberData interface
 * Triển khai của giao diện NumberData
 */
public class NumberData implements INumberData {
    /**
     * Whether the number is negative
     * Liệu số có âm hay không
     */
    private final boolean isNegative;

    /**
     * The integral part of the number
     * Phần nguyên của số
     */
    private final String integralPart;

    /**
     * The fractional part of the number
     * Phần thập phân của số
     */
    private final String fractionalPart;

    /**
     * Creates a new NumberData instance
     * Tạo một thể hiện NumberData mới
     *
     * @param isNegative     Whether the number is negative (Liệu số có âm hay không)
     * @param integralPart   The integral part (Phần nguyên)
     * @param fractionalPart The fractional part (Phần thập phân)
     */
    public NumberData(boolean isNegative, String integralPart, String fractionalPart) {
        this.isNegative = isNegative;
        this.integralPart = integralPart;
        this.fractionalPart = fractionalPart;
    }

    /**
     * Checks if the number is negative
     * Kiểm tra xem số có âm hay không
     *
     * @return true if negative, false otherwise (true nếu âm, ngược lại false)
     */
    @Override
    public boolean isNegative() {
        return isNegative;
    }

    /**
     * Gets the integral part of the number
     * Lấy phần nguyên của số
     *
     * @return The integral part as a string (Phần nguyên dưới dạng chuỗi)
     */
    @Override
    public String getIntegralPart() {
        return integralPart;
    }

    /**
     * Gets the fractional part of the number
     * Lấy phần thập phân của số
     *
     * @return The fractional part as a string (Phần thập phân dưới dạng chuỗi)
     */
    @Override
    public String getFractionalPart() {
        return fractionalPart;
    }
} 