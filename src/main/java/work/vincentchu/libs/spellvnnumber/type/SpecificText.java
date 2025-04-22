package work.vincentchu.libs.spellvnnumber.type;

/**
 * Implementation of ISpecificText interface for Vietnamese number spelling
 * Triển khai của giao diện ISpecificText cho việc đọc số tiếng Việt
 */
public class SpecificText implements ISpecificText {
    /**
     * Text for odd positions ('lẻ')
     * Văn bản cho các vị trí lẻ ('lẻ')
     */
    private String oddText = "lẻ";

    /**
     * Text for tens ('mười')
     * Văn bản cho hàng chục ('mười')
     */
    private String tenText = "mười";

    /**
     * Special tone text for one ('mốt')
     * Văn bản âm điệu đặc biệt cho số một ('mốt')
     */
    private String oneToneText = "mốt";

    /**
     * Special tone text for four ('tư')
     * Văn bản âm điệu đặc biệt cho số bốn ('tư')
     */
    private String fourToneText = "tư";

    /**
     * Special tone text for five ('lăm')
     * Văn bản âm điệu đặc biệt cho số năm ('lăm')
     */
    private String fiveToneText = "lăm";

    /**
     * Gets the text for odd positions ('lẻ')
     * Lấy văn bản cho các vị trí lẻ ('lẻ')
     */
    @Override
    public String getOddText() {
        return oddText;
    }

    /**
     * Sets the text for odd positions
     * Đặt văn bản cho các vị trí lẻ
     * @param oddText the text for odd positions to set
     */
    public void setOddText(String oddText) {
        this.oddText = oddText;
    }

    /**
     * Gets the text for tens ('mười')
     * Lấy văn bản cho hàng chục ('mười')
     * @return the text for tens
     */
    @Override
    public String getTenText() {
        return tenText;
    }

    /**
     * Sets the text for tens
     * Đặt văn bản cho hàng chục
     * @param tenText the text for tens to set
     */
    public void setTenText(String tenText) {
        this.tenText = tenText;
    }

    /**
     * Gets the special tone text for one ('mốt')
     * Lấy văn bản âm điệu đặc biệt cho số một ('mốt')
     * @return the special tone text for one
     */
    @Override
    public String getOneToneText() {
        return oneToneText;
    }

    /**
     * Sets the special tone text for one
     * Đặt văn bản âm điệu đặc biệt cho số một
     * @param oneToneText the special tone text for one to set
     */
    public void setOneToneText(String oneToneText) {
        this.oneToneText = oneToneText;
    }

    /**
     * Gets the special tone text for four ('tư')
     * Lấy văn bản âm điệu đặc biệt cho số bốn ('tư')
     * @return the special tone text for four
     */
    @Override
    public String getFourToneText() {
        return fourToneText;
    }

    /**
     * Sets the special tone text for four
     * Đặt văn bản âm điệu đặc biệt cho số bốn
     * @param fourToneText the special tone text for four to set
     */
    public void setFourToneText(String fourToneText) {
        this.fourToneText = fourToneText;
    }

    /**
     * Gets the special tone text for five ('lăm')
     * Lấy văn bản âm điệu đặc biệt cho số năm ('lăm')
     * @return the special tone text for five
     */
    @Override
    public String getFiveToneText() {
        return fiveToneText;
    }

    /**
     * Sets the special tone text for five
     * Đặt văn bản âm điệu đặc biệt cho số năm
     * @param fiveToneText the special tone text for five to set
     */
    public void setFiveToneText(String fiveToneText) {
        this.fiveToneText = fiveToneText;
    }
}
