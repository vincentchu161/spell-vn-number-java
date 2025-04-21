package work.vincentchu.libs.spellvnnumber.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import work.vincentchu.libs.spellvnnumber.SpellerConfig;
import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.error.InvalidNumberException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {
    private SpellerConfig config;

    @BeforeEach
    void setUp() {
        config = new SpellerConfig();
    }

    @Test
    void testCleanInputNumber() {
        assertEquals("123", NumberUtils.cleanInputNumber(123, config));
        assertEquals("123.45", NumberUtils.cleanInputNumber(123.45, config));
        assertEquals("-123", NumberUtils.cleanInputNumber(-123, config));
        assertEquals("123", NumberUtils.cleanInputNumber("123", config));
        assertEquals("123.45", NumberUtils.cleanInputNumber("123.45", config));
        assertEquals("123456", NumberUtils.cleanInputNumber("123,456", config));
        assertEquals("123456", NumberUtils.cleanInputNumber("123 456", config));
    }

    @Test
    void testCleanInputNumberWithCustomSeparators() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setDecimalPoint(",");
        customConfig.setThousandSign(".");

        assertEquals("123,45", NumberUtils.cleanInputNumber("123,45", customConfig));
        assertEquals("123456", NumberUtils.cleanInputNumber("123.456", customConfig));
        assertEquals("123456", NumberUtils.cleanInputNumber("123 456", customConfig));
    }

    @Test
    void testCleanInputNumberInvalidInput() {
        assertThrows(InvalidFormatException.class, () -> NumberUtils.cleanInputNumber(null, config));
        assertThrows(InvalidFormatException.class, () -> NumberUtils.cleanInputNumber("", config));
        assertThrows(InvalidNumberException.class, () -> NumberUtils.cleanInputNumber("abc", config));
        assertThrows(InvalidNumberException.class, () -> NumberUtils.cleanInputNumber("1.2.3", config));
    }

    @Test
    void testConvertScientificToDecimal() {
        // Test positive exponents
        assertEquals("1", NumberUtils.convertScientificToDecimal(1e0));
        assertEquals("10", NumberUtils.convertScientificToDecimal(1e1));
        assertEquals("100", NumberUtils.convertScientificToDecimal(1e2));
        assertEquals("1000", NumberUtils.convertScientificToDecimal(1e3));
        assertEquals("10000", NumberUtils.convertScientificToDecimal(1e4));
        assertEquals("100000", NumberUtils.convertScientificToDecimal(1e5));
        assertEquals("1000000", NumberUtils.convertScientificToDecimal(1e6));
        assertEquals("10000000", NumberUtils.convertScientificToDecimal(1e7));
        assertEquals("100000000", NumberUtils.convertScientificToDecimal(1e8));
        assertEquals("1000000000", NumberUtils.convertScientificToDecimal(1e9));

        // Test negative exponents
        assertEquals("0.1", NumberUtils.convertScientificToDecimal(1e-1));
        assertEquals("0.01", NumberUtils.convertScientificToDecimal(1e-2));
        assertEquals("0.001", NumberUtils.convertScientificToDecimal(1e-3));
        assertEquals("0.0001", NumberUtils.convertScientificToDecimal(1e-4));
        assertEquals("0.00001", NumberUtils.convertScientificToDecimal(1e-5));

        // Test decimal numbers
        assertEquals("12.34", NumberUtils.convertScientificToDecimal(1.234e1));
        assertEquals("123.4", NumberUtils.convertScientificToDecimal(1.234e2));
        assertEquals("1234", NumberUtils.convertScientificToDecimal(1.234e3));
        assertEquals("0.1234", NumberUtils.convertScientificToDecimal(1.234e-1));
        assertEquals("0.01234", NumberUtils.convertScientificToDecimal(1.234e-2));

        // Test negative numbers
        assertEquals("-1", NumberUtils.convertScientificToDecimal(-1e0));
        assertEquals("-10", NumberUtils.convertScientificToDecimal(-1e1));
        assertEquals("-0.1", NumberUtils.convertScientificToDecimal(-1e-1));
        assertEquals("-12.34", NumberUtils.convertScientificToDecimal(-1.234e1));

        // Test non-scientific notation numbers
        assertEquals("123.45", NumberUtils.convertScientificToDecimal(123.45));
        assertEquals("-123.45", NumberUtils.convertScientificToDecimal(-123.45));
        assertEquals("123", NumberUtils.convertScientificToDecimal(123.0));
    }

    @Test
    void testNormalizeNumberString() {
        assertEquals("123", NumberUtils.normalizeNumberString("123", config));
        assertEquals("123.45", NumberUtils.normalizeNumberString("123.45", config));
        assertEquals("123456", NumberUtils.normalizeNumberString("123,456", config));
        assertEquals("123456", NumberUtils.normalizeNumberString("123 456", config));
        assertEquals("-123", NumberUtils.normalizeNumberString("-123", config));
    }

    @Test
    void testNormalizeNumberStringWithCustomSeparators() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setDecimalPoint(",");
        customConfig.setThousandSign(".");

        assertEquals("123,45", NumberUtils.normalizeNumberString("123,45", customConfig));
        assertEquals("123456", NumberUtils.normalizeNumberString("123.456", customConfig));
        assertEquals("123456", NumberUtils.normalizeNumberString("123 456", customConfig));
    }

    @Test
    void testTrimLeft() {
        assertEquals("123", NumberUtils.trimLeft("00123", "0"));
        assertEquals("123", NumberUtils.trimLeft("000123", "0"));
        assertEquals("123", NumberUtils.trimLeft("123", "0"));
        assertEquals("0", NumberUtils.trimLeft("000", "0"));
    }

    @Test
    void testTrimRight() {
        assertEquals("123", NumberUtils.trimRight("12300", "0", false));
        assertEquals("123", NumberUtils.trimRight("123000", "0", false));
        assertEquals("123", NumberUtils.trimRight("123", "0", false));
        assertEquals("", NumberUtils.trimRight("000", "0", false));
        assertEquals("0", NumberUtils.trimRight("000", "0", true));
    }

    @Test
    void testTrimRedundantZeros() {
        assertEquals("123", NumberUtils.trimRedundantZeros(config, "00123"));
        assertEquals("123", NumberUtils.trimRedundantZeros(config, "12300"));
        assertEquals("123.45", NumberUtils.trimRedundantZeros(config, "123.4500"));
        assertEquals("123.45", NumberUtils.trimRedundantZeros(config, "00123.4500"));
        assertEquals("0", NumberUtils.trimRedundantZeros(config, "000"));
    }

    @Test
    void testTrimRedundantZerosWithCustomDecimalPoint() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setDecimalPoint(",");
        customConfig.setKeepOneZeroWhenAllZeros(true);

        assertEquals("123,45", NumberUtils.trimRedundantZeros(customConfig, "123,4500"));
        assertEquals("123,45", NumberUtils.trimRedundantZeros(customConfig, "00123,4500"));
    }
} 