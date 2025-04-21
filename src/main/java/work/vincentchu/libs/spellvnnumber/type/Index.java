package work.vincentchu.libs.spellvnnumber.type;

public enum Index {
    // Group indices (chỉ số nhóm)
    BILLION(0),  // tỷ
    MILLION(1),  // triệu
    THOUSAND(2), // nghìn

    // Position indices (chỉ số vị trí)
    HUNDREDS(3), // trăm
    TENS(4),     // mươi
    UNITS(5);    // (empty)

    private final int value;

    Index(int value) {
        this.value = value;
    }

    /***/
    public int value() {
        return value;
    }
} 