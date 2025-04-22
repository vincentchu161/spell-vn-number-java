package work.vincentchu.libs.spellvnnumber.type;

/**
 * Interface for special text handling in Vietnamese number spelling
 * Giao diện cho việc xử lý văn bản đặc biệt trong việc đọc số tiếng Việt
 */
public interface ISpecificText {
    /**
     * Gets the text for odd positions ('lẻ')
     * Lấy văn bản cho các vị trí lẻ ('lẻ')
     * @return the text for odd positions
     */
    String getOddText();

    /**
     * Gets the text for tens ('mười')
     * Lấy văn bản cho hàng chục ('mười')
     * @return the text for tens
     */
    String getTenText();

    /**
     * Gets the special tone text for one ('mốt')
     * Lấy văn bản âm điệu đặc biệt cho số một ('mốt')
     * @return the special tone text for one
     */
    String getOneToneText();

    /**
     * Gets the special tone text for four ('tư')
     * Lấy văn bản âm điệu đặc biệt cho số bốn ('tư')
     * @return the special tone text for four
     */
    String getFourToneText();

    /**
     * Gets the special tone text for five ('lăm')
     * Lấy văn bản âm điệu đặc biệt cho số năm ('lăm')
     * @return the special tone text for five
     */
    String getFiveToneText();
}
