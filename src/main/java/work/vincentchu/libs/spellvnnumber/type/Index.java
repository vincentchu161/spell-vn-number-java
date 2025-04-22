package work.vincentchu.libs.spellvnnumber.type;

/**
 * Enum representing magnitude indices and position indices for number spelling
 * Enum đại diện cho các chỉ số cỡ và chỉ số vị trí để đọc số
 */
public enum Index {
    /**
     * Billion magnitude index
     * Chỉ số cỡ tỷ
     */
    BILLION(0),

    /**
     * Million magnitude index
     * Chỉ số cỡ triệu
     */
    MILLION(1),

    /**
     * Thousand magnitude index
     * Chỉ số cỡ nghìn
     */
    THOUSAND(2),

    /**
     * Hundreds position index
     * Chỉ số vị trí hàng trăm
     */
    HUNDREDS(3),

    /**
     * Tens position index
     * Chỉ số vị trí hàng chục
     */
    TENS(4),

    /**
     * Units position index
     * Chỉ số vị trí hàng đơn vị
     */
    UNITS(5);

    private final int value;

    /**
     * Constructor for Index enum
     * Constructor cho enum Index
     *
     * @param value The numerical value of the index (Giá trị số của chỉ số)
     */
    Index(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value of the index
     * Lấy giá trị số của chỉ số
     *
     * @return The numerical value (Giá trị số)
     */
    public int value() {
        return value;
    }
} 