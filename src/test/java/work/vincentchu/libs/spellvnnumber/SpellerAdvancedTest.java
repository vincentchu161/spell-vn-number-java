package work.vincentchu.libs.spellvnnumber;

import org.junit.jupiter.api.Test;
import work.vincentchu.libs.spellvnnumber.type.Index;
import work.vincentchu.libs.spellvnnumber.util.NumberUtils;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Advanced tests for the Speller class
 */
class SpellerAdvancedTest {
    private final Speller speller = new Speller();

    @Test
    void testBoundaryCasesAndEdgeValues() {
        // Test with large numbers
        String largeNumber = NumberUtils.repeatString("9", 30);
        String result = speller.spell(largeNumber);
        assertTrue(result.length() > 100);

        // Test with precise decimal numbers
        String preciseNumber = "0." + NumberUtils.repeatString("1", 20);
        result = speller.spell(preciseNumber);
        assertTrue(result.contains("chấm"));
        assertTrue(result.split("chấm")[1].trim().length() > 10);

        // Test with MAX_VALUE of Long
        result = speller.spell(Long.MAX_VALUE);
        assertTrue(result.length() > 50);
    }

    @Test
    void testCustomUnitNames() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setUnitName(Index.BILLION.value(), "tỉ");
        customConfig.setUnitName(Index.THOUSAND.value(), "ngàn");
        Speller customSpeller = new Speller(customConfig);

        assertEquals("Một ngàn", customSpeller.spell(1000));
        assertEquals("Một tỉ", customSpeller.spell(1000000000));
        assertEquals("Một triệu", customSpeller.spell(1000000));
    }

    @Test
    void testCustomDigitNames() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setDigitName("4", "tư");
        customConfig.setDigitName("5", "lăm");
        Speller customSpeller = new Speller(customConfig);

        assertEquals("Tư mươi lăm", customSpeller.spell(45));
    }

    @Test
    void testDifferentDecimalPointText() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setPointText("phẩy");
        Speller customSpeller = new Speller(customConfig);

        assertEquals("Một phẩy năm", customSpeller.spell(1.5));
    }

    @Test
    void testDifferentSeparators() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setSeparator("_");
        Speller customSpeller = new Speller(customConfig);

        assertEquals("Một_trăm_hai_mươi_ba", customSpeller.spell(123));
    }

    @Test
    void testCustomDecimalHandlingConfiguration() {
        SpellerConfig config = new SpellerConfig();
        config.setKeepOneZeroWhenAllZeros(true);
        Speller customSpeller = new Speller(config);

        assertTrue(customSpeller.spell("10.0").contains("chấm không"));
    }

    @Test
    void testCustomSpecificText() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.getSpecificText().setOddText("lẻ");
        customConfig.getSpecificText().setTenText("mười");
        customConfig.getSpecificText().setOneToneText("mốt");
        customConfig.getSpecificText().setFourToneText("tư");
        customConfig.getSpecificText().setFiveToneText("lăm");
        Speller customSpeller = new Speller(customConfig);

        assertEquals("Một trăm lẻ một", customSpeller.spell(101));
        assertEquals("Hai mươi mốt", customSpeller.spell(21));
        assertEquals("Hai mươi tư", customSpeller.spell(24));
        assertEquals("Hai mươi lăm", customSpeller.spell(25));
    }

    @Test
    void testSpecialLeCaseCorrectly() {
        // Cases where "lẻ" should be used
        assertEquals("Một trăm lẻ một", speller.spell(101));
        assertEquals("Một nghìn không trăm lẻ một", speller.spell(1001));
        assertEquals("Mười nghìn không trăm lẻ một", speller.spell(10001));

        // Cases where "lẻ" should not be used
        assertEquals("Một trăm mười", speller.spell(110));
        assertEquals("Một nghìn không trăm mười", speller.spell(1010));
    }

    @Test
    void testSpecialPronunciationRules() {
        // Special cases for "một" -> "mốt"
        assertEquals("Hai mươi mốt", speller.spell(21));
        assertEquals("Ba mươi mốt", speller.spell(31));

        // Special cases for "bốn" -> "tư"
        assertEquals("Hai mươi tư", speller.spell(24));
        assertEquals("Ba mươi tư", speller.spell(34));

        // Special cases for "năm" -> "lăm"
        assertEquals("Hai mươi lăm", speller.spell(25));
        assertEquals("Ba mươi lăm", speller.spell(35));

        // Regular usage
        assertEquals("Một", speller.spell(1));
        assertEquals("Bốn", speller.spell(4));
        assertEquals("Năm", speller.spell(5));
    }

    @Test
    void testHandleCompactInputFormats() {
        // Without separators
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell(1234567));

        // With various types of input
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell(1234567));
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell(new BigInteger("1234567")));
    }

    @Test
    void testHandleComplexCombinationsOfNumbers() {
        assertEquals(
            "Một tỷ hai trăm nghìn không trăm ba mươi tư tỷ năm trăm sáu mươi bảy nghìn tám trăm chín mươi",
            speller.spell("1,000,200,034,000,567,890")
        );
    }

    @Test
    void testHandleBigIntegerNumbers() {
        // Test with BigInteger
        assertEquals(
            "Chín triệu không trăm lẻ bảy nghìn một trăm chín mươi chín tỷ hai trăm năm mươi tư triệu bảy trăm bốn mươi nghìn chín trăm chín mươi hai",
            speller.spell(new BigInteger("9007199254740992"))
        );
    }

    @Test
    void testHandleVeryLargeNumbers() {
        assertEquals("Một tỷ tỷ", speller.spell("1000000000000000000"));
    }

    @Test
    void testHandleNumbersWithManyDecimalPlaces() {
        assertEquals(
            "Một trăm hai mươi ba chấm bốn trăm năm mươi sáu nghìn bảy trăm tám mươi chín",
            speller.spell("123.456789")
        );
    }

    @Test
    void testHandleNumbersWithLeadingZeros() {
        assertEquals("Một trăm hai mươi ba", speller.spell("00123"));
    }

    @Test
    void testHandleNumbersWithTrailingZerosAfterDecimal() {
        assertEquals("Một trăm hai mươi ba chấm bốn mươi lăm", speller.spell("123.45000"));
    }

    @Test
    void testCurrencyUnit() {
        SpellerConfig config = new SpellerConfig();
        config.setCurrencyUnit("đồng");
        Speller customSpeller = new Speller(config);

        assertEquals("Một trăm hai mươi ba nghìn bốn trăm năm mươi sáu đồng", customSpeller.spell(123456));
        assertEquals("Một nghìn hai trăm ba mươi tư chấm năm mươi sáu đồng", customSpeller.spell(1234.56));
    }

    @Test
    void testCapitalization() {
        SpellerConfig config = new SpellerConfig();
        config.setCapitalizeInitial(false);
        Speller customSpeller = new Speller(config);

        assertEquals("một trăm hai mươi ba", customSpeller.spell(123));
        assertEquals("một nghìn hai trăm ba mươi tư chấm năm mươi sáu", customSpeller.spell(1234.56));
        assertEquals("âm một triệu", customSpeller.spell(-1000000));
    }
} 