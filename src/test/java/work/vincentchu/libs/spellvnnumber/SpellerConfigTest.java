package work.vincentchu.libs.spellvnnumber;

import org.junit.jupiter.api.Test;
import work.vincentchu.libs.spellvnnumber.type.INumberData;
import work.vincentchu.libs.spellvnnumber.type.Index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the SpellerConfig class
 */
class SpellerConfigTest {

    @Test
    void testDefaultConfiguration() {
        SpellerConfig config = new SpellerConfig();

        // Test defaults for basic properties
        assertEquals(" ", config.getSeparator());
        assertEquals("-", config.getNegativeSign());
        assertEquals(".", config.getDecimalPoint());
        assertEquals(",", config.getThousandSign());
        assertEquals("âm", config.getNegativeText());
        assertEquals("chấm", config.getPointText());
        assertTrue(config.isCapitalizeInitial());
        assertEquals("", config.getCurrencyUnit());
        assertEquals("0", config.getRedundantZeroChar());
        assertFalse(config.isKeepOneZeroWhenAllZeros());

        // Test defaults for digit names
        assertEquals("không", config.getDigitName("0"));
        assertEquals("một", config.getDigitName("1"));
        assertEquals("hai", config.getDigitName("2"));
        assertEquals("ba", config.getDigitName("3"));
        assertEquals("bốn", config.getDigitName("4"));
        assertEquals("năm", config.getDigitName("5"));
        assertEquals("sáu", config.getDigitName("6"));
        assertEquals("bảy", config.getDigitName("7"));
        assertEquals("tám", config.getDigitName("8"));
        assertEquals("chín", config.getDigitName("9"));

        // Test defaults for unit names
        assertEquals("tỷ", config.getUnitName(Index.BILLION.value()));
        assertEquals("triệu", config.getUnitName(Index.MILLION.value()));
        assertEquals("nghìn", config.getUnitName(Index.THOUSAND.value()));
        assertEquals("trăm", config.getUnitName(Index.HUNDREDS.value()));
        assertEquals("mươi", config.getUnitName(Index.TENS.value()));
        assertEquals("", config.getUnitName(Index.UNITS.value()));

        // Test defaults for specific text
        assertEquals("lẻ", config.getOddText());
        assertEquals("mười", config.getTenText());
        assertEquals("mốt", config.getOneToneText());
        assertEquals("tư", config.getFourToneText());
        assertEquals("lăm", config.getFiveToneText());
    }

    @Test
    void testCopyConstructor() {
        SpellerConfig original = new SpellerConfig();
        original.setSeparator("-");
        original.setNegativeSign("~");
        original.setDecimalPoint(",");
        original.setThousandSign(".");
        original.setNegativeText("negative");
        original.setPointText("point");
        original.setCapitalizeInitial(false);
        original.setCurrencyUnit("USD");
        original.setRedundantZeroChar("x");
        original.setKeepOneZeroWhenAllZeros(true);

        // Custom digit names
        original.setDigitName("1", "one");
        original.setDigitName("2", "two");

        // Custom unit names
        original.setUnitName(Index.BILLION.value(), "billion");
        original.setUnitName(Index.THOUSAND.value(), "thousand");

        // Custom specific text
        original.getSpecificText().setOddText("odd");
        original.getSpecificText().setTenText("ten");

        // Create a copy
        SpellerConfig copy = new SpellerConfig(original);

        // Test that copy has same values
        assertEquals("-", copy.getSeparator());
        assertEquals("~", copy.getNegativeSign());
        assertEquals(",", copy.getDecimalPoint());
        assertEquals(".", copy.getThousandSign());
        assertEquals("negative", copy.getNegativeText());
        assertEquals("point", copy.getPointText());
        assertFalse(copy.isCapitalizeInitial());
        assertEquals("USD", copy.getCurrencyUnit());
        assertEquals("x", copy.getRedundantZeroChar());
        assertTrue(copy.isKeepOneZeroWhenAllZeros());

        // Test copied digit names
        assertEquals("one", copy.getDigitName("1"));
        assertEquals("two", copy.getDigitName("2"));

        // Test copied unit names
        assertEquals("billion", copy.getUnitName(Index.BILLION.value()));
        assertEquals("thousand", copy.getUnitName(Index.THOUSAND.value()));

        // Test copied specific text
        assertEquals("odd", copy.getOddText());
        assertEquals("ten", copy.getTenText());
    }

    @Test
    void testParseNumberData() {
        SpellerConfig config = new SpellerConfig();

        // Test positive integer
        INumberData data = config.parseNumberData("123");
        assertFalse(data.isNegative());
        assertEquals("123", data.getIntegralPart());
        assertEquals("", data.getFractionalPart());

        // Test negative integer
        data = config.parseNumberData("-456");
        assertTrue(data.isNegative());
        assertEquals("456", data.getIntegralPart());
        assertEquals("", data.getFractionalPart());

        // Test positive decimal
        data = config.parseNumberData("123.45");
        assertFalse(data.isNegative());
        assertEquals("123", data.getIntegralPart());
        assertEquals("45", data.getFractionalPart());

        // Test negative decimal
        data = config.parseNumberData("-789.01");
        assertTrue(data.isNegative());
        assertEquals("789", data.getIntegralPart());
        assertEquals("01", data.getFractionalPart());

        // Test with leading zeros
        data = config.parseNumberData("000123");
        assertFalse(data.isNegative());
        assertEquals("123", data.getIntegralPart());
        assertEquals("", data.getFractionalPart());

        // Test with trailing zeros
        data = config.parseNumberData("123.4500");
        assertFalse(data.isNegative());
        assertEquals("123", data.getIntegralPart());
        assertEquals("45", data.getFractionalPart());

        // Test zero
        data = config.parseNumberData("0");
        assertFalse(data.isNegative());
        assertEquals("0", data.getIntegralPart());
        assertEquals("", data.getFractionalPart());

        // Test zero with decimal
        data = config.parseNumberData("0.0");
        assertFalse(data.isNegative());
        assertEquals("0", data.getIntegralPart());
        assertEquals("", data.getFractionalPart());
    }

    @Test
    void testSettersAndGetters() {
        SpellerConfig config = new SpellerConfig();

        // Test setters and getters for basic properties
        config.setSeparator("-");
        assertEquals("-", config.getSeparator());

        config.setNegativeSign("~");
        assertEquals("~", config.getNegativeSign());

        config.setDecimalPoint(",");
        assertEquals(",", config.getDecimalPoint());

        config.setThousandSign(".");
        assertEquals(".", config.getThousandSign());

        config.setNegativeText("negative");
        assertEquals("negative", config.getNegativeText());

        config.setPointText("point");
        assertEquals("point", config.getPointText());

        config.setCapitalizeInitial(false);
        assertFalse(config.isCapitalizeInitial());

        config.setCurrencyUnit("USD");
        assertEquals("USD", config.getCurrencyUnit());

        config.setRedundantZeroChar("x");
        assertEquals("x", config.getRedundantZeroChar());

        config.setKeepOneZeroWhenAllZeros(true);
        assertTrue(config.isKeepOneZeroWhenAllZeros());

        // Test setters and getters for digit names
        config.setDigitName("1", "one");
        assertEquals("one", config.getDigitName("1"));

        // Test setters and getters for unit names
        config.setUnitName(Index.BILLION.value(), "billion");
        assertEquals("billion", config.getUnitName(Index.BILLION.value()));
    }

    @Test
    void testUnitNameOfMagnitude() {
        SpellerConfig config = new SpellerConfig();

        // By default, unitNameOfMagnitude should return the unit name of the next lower magnitude
        assertEquals("triệu", config.getUnitNameOfMagnitude(Index.BILLION.value()));
        assertEquals("nghìn", config.getUnitNameOfMagnitude(Index.MILLION.value()));
        assertEquals("", config.getUnitNameOfMagnitude(Index.THOUSAND.value()));

        // Customize unit names and test again
        config.setUnitName(Index.BILLION.value(), "billion");
        config.setUnitName(Index.MILLION.value(), "million");
        config.setUnitName(Index.THOUSAND.value(), "thousand");

        assertEquals("million", config.getUnitNameOfMagnitude(Index.BILLION.value()));
        assertEquals("thousand", config.getUnitNameOfMagnitude(Index.MILLION.value()));
        assertEquals("", config.getUnitNameOfMagnitude(Index.THOUSAND.value()));
    }
} 