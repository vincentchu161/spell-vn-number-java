package work.vincentchu.libs.spellvnnumber;

import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.type.INumberData;
import work.vincentchu.libs.spellvnnumber.type.Index;
import work.vincentchu.libs.spellvnnumber.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for spelling numbers in Vietnamese
 * <p>
 * Lớp chính để đọc số thành chữ trong tiếng Việt. Lớp này cung cấp các phương thức
 * để chuyển đổi số thành chuỗi văn bản tiếng Việt dựa trên cấu hình được cung cấp.
 */
public class Speller {
    /**
     * Number of magnitude groups in the system (billions, millions, thousands)
     * Số lượng nhóm đơn vị trong hệ thống (tỷ, triệu, nghìn)
     */
    private static final int NUM_GROUPS = 3;

    /**
     * Number of positions in each group of digits (hundreds, tens, units)
     * Số lượng vị trí trong mỗi nhóm chữ số (hàng trăm, hàng chục, hàng đơn vị)
     */
    private static final int NUM_POSITIONS = 3;

    /**
     * Configuration for the speller
     * Cấu hình cho bộ đọc số
     */
    private final SpellerConfig config;

    /**
     * Creates a new speller with default configuration
     * Tạo một bộ đọc số mới với cấu hình mặc định
     */
    public Speller() {
        this.config = new SpellerConfig();
    }

    /**
     * Creates a new speller with the specified configuration
     * Tạo một bộ đọc số mới với cấu hình được chỉ định
     *
     * @param config Cấu hình cho bộ đọc số
     */
    public Speller(SpellerConfig config) {
        this.config = new SpellerConfig(config);
    }

    /**
     * Spells a number in Vietnamese
     * Đọc một số thành chữ trong tiếng Việt
     *
     * @param input The number to spell (Số cần đọc)
     * @return The spelled number in Vietnamese (Chuỗi văn bản tiếng Việt của số)
     * @throws InvalidFormatException if input is null or invalid format
     */
    public String spell(Object input) {
        if (input == null) {
            throw new InvalidFormatException("Input cannot be null");
        }

        // Parse the number using the configurable parser
        // Phân tích số bằng parser có thể cấu hình
        INumberData numberData = config.parseNumberData(input);

        List<String> spelledParts = new ArrayList<>();

        // Add negative sign if needed (Thêm dấu âm nếu cần)
        if (numberData.isNegative()) {
            spelledParts.add(config.getNegativeText());
        }

        // Process integral part (Xử lý phần nguyên)
        processPart(spelledParts, numberData.getIntegralPart());

        // Process fractional part if exists (Xử lý phần thập phân nếu có)
        if (!numberData.getFractionalPart().isEmpty()) {
            spelledParts.add(config.getPointText());
            processPart(spelledParts, numberData.getFractionalPart());
        }

        // Capitalize the first letter if capitalizeInitial is true
        // Viết hoa chữ cái đầu tiên nếu capitalizeInitial là true
        if (config.isCapitalizeInitial() && !spelledParts.isEmpty()) {
            String firstPart = spelledParts.get(0);
            spelledParts.set(0, firstPart.substring(0, 1).toUpperCase() + firstPart.substring(1));
        }

        // Join all parts with the separator
        // Kết hợp tất cả các phần với dấu phân cách
        String result = String.join(config.getSeparator(), spelledParts);

        // After joining all parts, append the currency unit if provided
        // Sau khi kết hợp tất cả các phần, thêm đơn vị tiền tệ nếu được cung cấp
        if (!config.getCurrencyUnit().isEmpty()) {
            result += " " + config.getCurrencyUnit();
        }

        return result;
    }

    /**
     * Process a section of the number by splitting it into groups and spelling each group
     * Xử lý một phần của số bằng cách chia nó thành các nhóm và đọc từng nhóm
     *
     * @param spelledParts List to add spelled parts to (Danh sách để thêm các phần đã đọc)
     * @param numberStr    The number string to process (Chuỗi số cần xử lý)
     */
    private void processPart(List<String> spelledParts, String numberStr) {
        if (numberStr.isEmpty()) {
            return;
        }

        // Add zeros to the beginning if length is not divisible by NUM_POSITIONS
        // Thêm các số 0 vào đầu nếu độ dài không chia hết cho NUM_POSITIONS
        int mod = numberStr.length() % NUM_POSITIONS;
        String paddedNumb = mod != 0 ? NumberUtils.repeatString("0", NUM_POSITIONS - mod) + numberStr : numberStr;

        int totalThreeDigitSegments = paddedNumb.length() / NUM_POSITIONS;

        // Calculate magnitude modifications and remaining groups
        // Tính toán các điều chỉnh cỡ và các nhóm còn lại
        int magnitudeMod = totalThreeDigitSegments % NUM_GROUPS;
        int remainingGroups = magnitudeMod == 0
                ? totalThreeDigitSegments / NUM_GROUPS
                : (totalThreeDigitSegments / NUM_GROUPS) + 1;

        int currentMagnitudeIndex = magnitudeMod != 0 ? NUM_GROUPS - magnitudeMod : Index.BILLION.value();

        boolean isFirst = true;
        int i = 0;

        while (remainingGroups > 0) {
            // Add unit for the group if not the first group
            // Thêm đơn vị cho nhóm nếu không phải nhóm đầu tiên
            if (!isFirst) {
                spelledParts.add(config.getUnitName(currentMagnitudeIndex));
            }

            // Process each position within the current magnitude group
            // Xử lý từng vị trí trong nhóm cỡ hiện tại
            for (; currentMagnitudeIndex <= Index.THOUSAND.value(); currentMagnitudeIndex++) {
                char hundredsDigit = paddedNumb.charAt(i++);
                char tensDigit = paddedNumb.charAt(i++);
                char unitsDigit = paddedNumb.charAt(i++);

                if (isFirst) {
                    // Special processing for the first group - skip leading zeros
                    // Xử lý đặc biệt cho nhóm đầu tiên - bỏ qua các số 0 đứng đầu
                    if (hundredsDigit != '0') {
                        isFirst = false;
                        spellHundreds(spelledParts, hundredsDigit, tensDigit, unitsDigit);
                    }
                    if (!isFirst || tensDigit != '0') {
                        isFirst = false;
                        spellTens(spelledParts, tensDigit, unitsDigit);
                    }
                    if (!isFirst || unitsDigit != '0') {
                        isFirst = false;
                        spellUnits(spelledParts, hundredsDigit, tensDigit, unitsDigit, currentMagnitudeIndex);
                    }
                    // If all digits are zero, return "không"
                    // Nếu tất cả các chữ số đều là 0, trả về "không"
                    if (isFirst) {
                        isFirst = false;
                        spelledParts.add(config.getDigitName("0"));
                    }
                } else {
                    // Process all digits for non-first groups
                    // Xử lý tất cả các chữ số cho các nhóm không phải đầu tiên
                    spellHundreds(spelledParts, hundredsDigit, tensDigit, unitsDigit);
                    spellTens(spelledParts, tensDigit, unitsDigit);
                    spellUnits(spelledParts, hundredsDigit, tensDigit, unitsDigit, currentMagnitudeIndex);
                }
            }

            remainingGroups--;
            currentMagnitudeIndex = Index.BILLION.value();
        }
    }

    /**
     * Spell a digit at the units position based on its context
     * Đọc chữ số ở vị trí đơn vị dựa trên ngữ cảnh của nó
     *
     * @param spelledParts          List to add spelled parts to (Danh sách để thêm các phần đã đọc)
     * @param hundredsDigit         Digit at hundreds position (Chữ số ở vị trí hàng trăm)
     * @param tensDigit             Digit at tens position (Chữ số ở vị trí hàng chục)
     * @param unitsDigit            Digit at units position (Chữ số ở vị trí đơn vị)
     * @param currentMagnitudeIndex Index of the magnitude type (thousand, million, billion)
     *                              (Chỉ số của loại cỡ - nghìn, triệu, tỷ)
     */
    private void spellUnits(List<String> spelledParts, char hundredsDigit, char tensDigit, char unitsDigit, int currentMagnitudeIndex) {
        if (unitsDigit == '0') {
            // Unit digit is zero, but hundreds or tens are not both zero
            // Chữ số đơn vị là 0, nhưng hàng trăm hoặc hàng chục không đồng thời là 0
            if (tensDigit != '0' || hundredsDigit != '0') {
                if (currentMagnitudeIndex != Index.THOUSAND.value()) {
                    spelledParts.add(config.getUnitNameOfMagnitude(currentMagnitudeIndex));
                }
            }
            return;
        }

        // Special rules for digits 1, 4, and 5 in Vietnamese
        // Quy tắc đặc biệt cho các chữ số 1, 4 và 5 trong tiếng Việt
        if (unitsDigit == '1') {
            if (tensDigit != '0' && tensDigit != '1') {
                spelledParts.add(config.getSpecificText().getOneToneText()); // "mốt" instead of "một"
            } else {
                spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
            }
        } else if (unitsDigit == '4') {
            if (tensDigit != '0' && tensDigit != '1') {
                spelledParts.add(config.getSpecificText().getFourToneText()); // "tư" instead of "bốn"
            } else {
                spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
            }
        } else if (unitsDigit == '5') {
            if (tensDigit != '0') {
                spelledParts.add(config.getSpecificText().getFiveToneText()); // "lăm" instead of "năm"
            } else {
                spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
            }
        } else {
            spelledParts.add(config.getDigitName(String.valueOf(unitsDigit)));
        }

        // Add magnitude unit if needed (except for thousands)
        // Thêm đơn vị cỡ nếu cần (trừ hàng nghìn)
        if (currentMagnitudeIndex != Index.THOUSAND.value()) {
            spelledParts.add(config.getUnitNameOfMagnitude(currentMagnitudeIndex));
        }
    }

    /**
     * Spell a digit at the tens position based on its context
     * Đọc chữ số ở vị trí hàng chục dựa trên ngữ cảnh của nó
     *
     * @param spelledParts List to add spelled parts to (Danh sách để thêm các phần đã đọc)
     * @param tensDigit    Digit at tens position (Chữ số ở vị trí hàng chục)
     * @param unitsDigit   Digit at units position (Chữ số ở vị trí đơn vị)
     */
    private void spellTens(List<String> spelledParts, char tensDigit, char unitsDigit) {
        if (tensDigit == '0') {
            // Add "lẻ" if tens digit is 0 but units digit is not
            // Thêm "lẻ" nếu chữ số hàng chục là 0 nhưng chữ số đơn vị không phải là 0
            if (unitsDigit != '0') {
                spelledParts.add(config.getSpecificText().getOddText());
            }
        } else if (tensDigit == '1') {
            // Special case for "10" in Vietnamese
            // Trường hợp đặc biệt cho "10" trong tiếng Việt
            spelledParts.add(config.getSpecificText().getTenText());
        } else {
            // Normal case for tens position
            // Trường hợp thông thường cho vị trí hàng chục
            spelledParts.add(config.getDigitName(String.valueOf(tensDigit)));
            spelledParts.add(config.getUnitName(Index.TENS.value()));
        }
    }

    /**
     * Spell a digit at the hundreds position based on its context
     * Đọc chữ số ở vị trí hàng trăm dựa trên ngữ cảnh của nó
     *
     * @param spelledParts  List to add spelled parts to (Danh sách để thêm các phần đã đọc)
     * @param hundredsDigit Digit at hundreds position (Chữ số ở vị trí hàng trăm)
     * @param tensDigit     Digit at tens position (Chữ số ở vị trí hàng chục)
     * @param unitsDigit    Digit at units position (Chữ số ở vị trí đơn vị)
     */
    private void spellHundreds(List<String> spelledParts, char hundredsDigit, char tensDigit, char unitsDigit) {
        if (hundredsDigit == '0') {
            // Only add "không trăm" if tens or units are not both zero
            // Chỉ thêm "không trăm" nếu hàng chục hoặc đơn vị không đồng thời là 0
            if (!(tensDigit == '0' && unitsDigit == '0')) {
                spelledParts.add(config.getDigitName(String.valueOf(hundredsDigit)));
                spelledParts.add(config.getUnitName(Index.HUNDREDS.value()));
            }
        } else {
            // Normal case for hundreds position
            // Trường hợp thông thường cho vị trí hàng trăm
            spelledParts.add(config.getDigitName(String.valueOf(hundredsDigit)));
            spelledParts.add(config.getUnitName(Index.HUNDREDS.value()));
        }
    }
} 