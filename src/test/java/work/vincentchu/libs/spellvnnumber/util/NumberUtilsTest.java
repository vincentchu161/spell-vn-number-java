package work.vincentchu.libs.spellvnnumber.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import work.vincentchu.libs.spellvnnumber.SpellerConfig;
import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.error.InvalidNumberException;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    void testCleanInputNumberWithDifferentTypes() {
        // Test with various integer types
        assertEquals("123", NumberUtils.cleanInputNumber((byte) 123, config));
        assertEquals("123", NumberUtils.cleanInputNumber((short) 123, config));
        assertEquals("123", NumberUtils.cleanInputNumber(123, config));
        assertEquals("123", NumberUtils.cleanInputNumber(123L, config));
        
        // Test with floating point types
        assertEquals("123.45", NumberUtils.cleanInputNumber(123.45f, config));
        assertEquals("123.45", NumberUtils.cleanInputNumber(123.45d, config));
        
        // Test with BigInteger and BigDecimal
        assertEquals("123", NumberUtils.cleanInputNumber(new BigInteger("123"), config));
        assertEquals("123.45", NumberUtils.cleanInputNumber(new BigDecimal("123.45"), config));
    }

    @Test
    void testToPlainString() {
        // Test positive exponents
        assertEquals("1", NumberUtils.toPlainString(1e0));
        assertEquals("10", NumberUtils.toPlainString(1e1));
        assertEquals("100", NumberUtils.toPlainString(1e2));
        assertEquals("1000", NumberUtils.toPlainString(1e3));
        assertEquals("10000", NumberUtils.toPlainString(1e4));
        assertEquals("100000", NumberUtils.toPlainString(1e5));
        assertEquals("1000000", NumberUtils.toPlainString(1e6));
        assertEquals("10000000", NumberUtils.toPlainString(1e7));
        assertEquals("100000000", NumberUtils.toPlainString(1e8));
        assertEquals("1000000000", NumberUtils.toPlainString(1e9));

        // Test negative exponents
        assertEquals("0.1", NumberUtils.toPlainString(1e-1));
        assertEquals("0.01", NumberUtils.toPlainString(1e-2));
        assertEquals("0.001", NumberUtils.toPlainString(1e-3));
        assertEquals("0.0001", NumberUtils.toPlainString(1e-4));
        assertEquals("0.00001", NumberUtils.toPlainString(1e-5));

        // Test decimal numbers
        assertEquals("12.34", NumberUtils.toPlainString(1.234e1));
        assertEquals("123.4", NumberUtils.toPlainString(1.234e2));
        assertEquals("1234", NumberUtils.toPlainString(1.234e3));
        assertEquals("0.1234", NumberUtils.toPlainString(1.234e-1));
        assertEquals("0.01234", NumberUtils.toPlainString(1.234e-2));

        // Test negative numbers
        assertEquals("-1", NumberUtils.toPlainString(-1e0));
        assertEquals("-10", NumberUtils.toPlainString(-1e1));
        assertEquals("-0.1", NumberUtils.toPlainString(-1e-1));
        assertEquals("-12.34", NumberUtils.toPlainString(-1.234e1));

        // Test non-scientific notation numbers
        assertEquals("123.45", NumberUtils.toPlainString(123.45));
        assertEquals("-123.45", NumberUtils.toPlainString(-123.45));
        assertEquals("123", NumberUtils.toPlainString(123.0));

        // Test trailing zeros
        assertEquals("100", NumberUtils.toPlainString(1e2));
        assertEquals("1000", NumberUtils.toPlainString(new BigDecimal("1000.0")));
        assertEquals("0.1", NumberUtils.toPlainString(new BigDecimal("0.100")));
    }

    @Test
    void testToPlainStringWithCustomSeparator() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setDecimalPoint(",");
        
        assertEquals("123,45", NumberUtils.toPlainString("123,45", customConfig.getDecimalPoint()));
        assertEquals("123,0", NumberUtils.toPlainString("123,0", customConfig.getDecimalPoint()));
        assertEquals("0,123", NumberUtils.toPlainString("0,123", customConfig.getDecimalPoint()));
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
    void testNormalizeNumberStringWithWhitespaceAndSpecialChars() {
        assertEquals("123", NumberUtils.normalizeNumberString("  123  ", config));
        assertEquals("123", NumberUtils.normalizeNumberString("1 2 3", config));
        assertEquals("-123", NumberUtils.normalizeNumberString(" - 1 2 3 ", config));
    }

    @Test
    void testTrimLeft() {
        assertEquals("123", NumberUtils.trimLeft("00123", "0"));
        assertEquals("123", NumberUtils.trimLeft("000123", "0"));
        assertEquals("123", NumberUtils.trimLeft("123", "0"));
        assertEquals("0", NumberUtils.trimLeft("000", "0"));
        
        // Test with other characters
        assertEquals("abc", NumberUtils.trimLeft("xxabc", "x"));
        assertEquals("abc", NumberUtils.trimLeft("abc", "x"));
    }

    @Test
    void testTrimRight() {
        assertEquals("123", NumberUtils.trimRight("12300", "0", false));
        assertEquals("123", NumberUtils.trimRight("123000", "0", false));
        assertEquals("123", NumberUtils.trimRight("123", "0", false));
        assertEquals("", NumberUtils.trimRight("000", "0", false));
        assertEquals("0", NumberUtils.trimRight("000", "0", true));
        
        // Test with keepOneZeroWhenAllZeros
        assertEquals("123.", NumberUtils.trimRight("123.000", "0", true));
        assertEquals("0.", NumberUtils.trimRight("0.000", "0", true));
        
        // Test with other characters
        assertEquals("abc", NumberUtils.trimRight("abcxx", "x", false));
        assertEquals("abc", NumberUtils.trimRight("abc", "x", false));
    }

    @Test
    void testTrimRedundantZeros() {
        assertEquals("123", NumberUtils.trimRedundantZeros(config, "00123"));
        assertEquals("12300", NumberUtils.trimRedundantZeros(config, "12300"));
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

    @Test
    void testTrimRedundantZerosWithKeepOneZeroWhenAllZeros() {
        SpellerConfig customConfig = new SpellerConfig();
        customConfig.setKeepOneZeroWhenAllZeros(true);

        // All zeros in fractional part - should keep one zero
        assertEquals("123.0", NumberUtils.trimRedundantZeros(customConfig, "123.000"));
        assertEquals("0.0", NumberUtils.trimRedundantZeros(customConfig, "0.000"));
        
        // Non-zero in fractional part - should trim trailing zeros
        assertEquals("123.45", NumberUtils.trimRedundantZeros(customConfig, "123.4500"));
        assertEquals("0.1", NumberUtils.trimRedundantZeros(customConfig, "0.1000"));
    }

    @Test
    void testRepeatString() {
        assertEquals("", NumberUtils.repeatString("x", 0));
        assertEquals("x", NumberUtils.repeatString("x", 1));
        assertEquals("xxx", NumberUtils.repeatString("x", 3));
        assertEquals("abcabc", NumberUtils.repeatString("abc", 2));
    }
} 