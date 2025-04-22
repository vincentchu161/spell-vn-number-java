package work.vincentchu.libs.spellvnnumber.type;

/**
 * Interface for parsed number data
 * Giao diện cho dữ liệu số đã được phân tích
 */
public interface INumberData {
    /**
     * Checks if the number is negative
     * Kiểm tra xem số có âm hay không
     *
     * @return true if negative, false otherwise (true nếu âm, ngược lại false)
     */
    boolean isNegative();

    /**
     * Gets the integral part of the number
     * Lấy phần nguyên của số
     *
     * @return The integral part as a string (Phần nguyên dưới dạng chuỗi)
     */
    String getIntegralPart();

    /**
     * Gets the fractional part of the number
     * Lấy phần thập phân của số
     *
     * @return The fractional part as a string (Phần thập phân dưới dạng chuỗi)
     */
    String getFractionalPart();
} 