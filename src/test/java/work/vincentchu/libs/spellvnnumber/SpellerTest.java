package work.vincentchu.libs.spellvnnumber;

import org.junit.jupiter.api.Test;
import work.vincentchu.libs.spellvnnumber.error.InvalidFormatException;
import work.vincentchu.libs.spellvnnumber.error.InvalidNumberException;
import work.vincentchu.libs.spellvnnumber.type.Index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpellerTest {
    private final Speller speller = new Speller();

    @Test
    void testBasicNumbers() {
        assertEquals("Không", speller.spell(0));
        assertEquals("Một", speller.spell(1));
        assertEquals("Mười", speller.spell(10));
        assertEquals("Mười một", speller.spell(11));
        assertEquals("Hai mươi", speller.spell(20));
        assertEquals("Hai mươi mốt", speller.spell(21));
        assertEquals("Một trăm", speller.spell(100));
        assertEquals("Một trăm lẻ một", speller.spell(101));
        assertEquals("Một trăm mười", speller.spell(110));
        assertEquals("Một trăm mười một", speller.spell(111));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("Một nghìn", speller.spell(1000));
        assertEquals("Một triệu", speller.spell(1000000));
        assertEquals("Một tỷ", speller.spell(1000000000));
        assertEquals("Một nghìn tỷ", speller.spell(1000000000000L));
        assertEquals("Một triệu tỷ", speller.spell(1000000000000000L));
        assertEquals("Một tỷ tỷ", speller.spell(1000000000000000000L));
    }

    @Test
    void testDecimalNumbers() {
        assertEquals("Không", speller.spell(0.0));
        assertEquals("Một chấm một", speller.spell(1.1));
        assertEquals("Mười chấm một", speller.spell(10.1));
        assertEquals("Một trăm chấm một", speller.spell(100.1));
        assertEquals("Một nghìn chấm một", speller.spell(1000.1));
        assertEquals("Một triệu chấm một", speller.spell(1000000.1));
        assertEquals("Một tỷ chấm một", speller.spell(1000000000.1));
    }

    @Test
    void testNegativeNumbers() {
        assertEquals("Âm một", speller.spell(-1));
        assertEquals("Âm mười", speller.spell(-10));
        assertEquals("Âm một trăm", speller.spell(-100));
        assertEquals("Âm một nghìn", speller.spell(-1000));
        assertEquals("Âm một triệu", speller.spell(-1000000));
        assertEquals("Âm một tỷ", speller.spell(-1000000000));
        assertEquals("Âm một chấm một", speller.spell(-1.1));
    }

    @Test
    void testSpecialCases() {
        assertEquals("Mười một", speller.spell(11));
        assertEquals("Hai mươi mốt", speller.spell(21));
        assertEquals("Ba mươi mốt", speller.spell(31));
        assertEquals("Bốn mươi mốt", speller.spell(41));
        assertEquals("Năm mươi mốt", speller.spell(51));
        assertEquals("Sáu mươi mốt", speller.spell(61));
        assertEquals("Bảy mươi mốt", speller.spell(71));
        assertEquals("Tám mươi mốt", speller.spell(81));
        assertEquals("Chín mươi mốt", speller.spell(91));

        assertEquals("Mười lăm", speller.spell(15));
        assertEquals("Hai mươi lăm", speller.spell(25));
        assertEquals("Ba mươi lăm", speller.spell(35));
        assertEquals("Bốn mươi lăm", speller.spell(45));
        assertEquals("Năm mươi lăm", speller.spell(55));
        assertEquals("Sáu mươi lăm", speller.spell(65));
        assertEquals("Bảy mươi lăm", speller.spell(75));
        assertEquals("Tám mươi lăm", speller.spell(85));
        assertEquals("Chín mươi lăm", speller.spell(95));

        assertEquals("Mười bốn", speller.spell(14));
        assertEquals("Hai mươi tư", speller.spell(24));
        assertEquals("Ba mươi tư", speller.spell(34));
        assertEquals("Bốn mươi tư", speller.spell(44));
        assertEquals("Năm mươi tư", speller.spell(54));
        assertEquals("Sáu mươi tư", speller.spell(64));
        assertEquals("Bảy mươi tư", speller.spell(74));
        assertEquals("Tám mươi tư", speller.spell(84));
        assertEquals("Chín mươi tư", speller.spell(94));
    }

    @Test
    void testCustomConfig() {
        SpellerConfig config = new SpellerConfig();
        config.setSeparator("-");
        config.setNegativeSign("minus");
        config.setDecimalPoint(",");
        config.setThousandSign(".");
        config.setNegativeText("negative");
        config.setPointText("point");
        config.setCapitalizeInitial(false);
        config.setCurrencyUnit("USD");
        config.setRedundantZeroChar("0");
        config.setKeepOneZeroWhenAllZeros(true);

        // Set custom digit names
        config.setDigitName("0", "zero");
        config.setDigitName("1", "one");
        config.setDigitName("2", "two");
        config.setDigitName("3", "three");
        config.setDigitName("4", "four");
        config.setDigitName("5", "five");
        config.setDigitName("6", "six");
        config.setDigitName("7", "seven");
        config.setDigitName("8", "eight");
        config.setDigitName("9", "nine");

        // Set custom unit names
        config.setUnitName(Index.BILLION.value(), "billion");
        config.setUnitName(Index.MILLION.value(), "million");
        config.setUnitName(Index.THOUSAND.value(), "thousand");
        config.setUnitName(Index.HUNDREDS.value(), "hundred");
        config.setUnitName(Index.TENS.value(), "ty");
        config.setUnitName(Index.UNITS.value(), "");

        Speller customSpeller = new Speller(config);
        assertEquals("negative-one-point-one-USD", customSpeller.spell(-1.1));
        assertEquals("one-thousand-point-one-USD", customSpeller.spell(1000.1));
    }

    @Test
    void testInvalidInput() {
        assertThrows(InvalidFormatException.class, () -> speller.spell(null));
        assertThrows(InvalidFormatException.class, () -> speller.spell(""));
        assertThrows(InvalidNumberException.class, () -> speller.spell("abc"));
        assertThrows(InvalidNumberException.class, () -> speller.spell("1.2.3"));
    }

    @Test
    void testDataTypes() {
        assertEquals("Một", speller.spell(1));
        assertEquals("Một", speller.spell(1L));
        assertEquals("Một", speller.spell(1.0f));
        assertEquals("Một", speller.spell(1.0));
        assertEquals("Một", speller.spell("1"));
        assertEquals("Một", speller.spell("1.0"));
        assertEquals("Một nghìn", speller.spell("1,000"));
        assertEquals("Một nghìn", speller.spell("1 000"));
    }

    @Test
    void testScientificNotation() {
        assertEquals("Một", speller.spell(1e0));
        assertEquals("Mười", speller.spell(1e1));
        assertEquals("Một trăm", speller.spell(1e2));
        assertEquals("Một nghìn", speller.spell(1e3));
        assertEquals("Mười nghìn", speller.spell(1e4));
        assertEquals("Một trăm nghìn", speller.spell(1e5));
        assertEquals("Một triệu", speller.spell(1e6));
        assertEquals("Mười triệu", speller.spell(1e7));
        assertEquals("Một trăm triệu", speller.spell(1e8));
        assertEquals("Một tỷ", speller.spell(1e9));
    }

    @Test
    void testEdgeCases() {
        assertEquals("Không", speller.spell(0));
        assertEquals("Không", speller.spell(0.0));
        assertEquals("Không", speller.spell(-0));
        assertEquals("Âm không", speller.spell(-0.0));
        assertEquals("Âm không", speller.spell("-0"));
        assertEquals("Âm không", speller.spell("-0.0"));
    }

    @Test
    void testComplexNumbers() {
        assertEquals("Một trăm hai mươi ba", speller.spell(123));
        assertEquals("Một nghìn hai trăm ba mươi tư", speller.spell(1234));
        assertEquals("Mười hai nghìn ba trăm bốn mươi lăm", speller.spell(12345));
        assertEquals("Một trăm hai mươi ba nghìn bốn trăm năm mươi sáu", speller.spell(123456));
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell(1234567));
        assertEquals("Mười hai triệu ba trăm bốn mươi lăm nghìn sáu trăm bảy mươi tám", speller.spell(12345678));
        assertEquals("Một trăm hai mươi ba triệu bốn trăm năm mươi sáu nghìn bảy trăm tám mươi chín", speller.spell(123456789));
    }

    @Test
    void testComplexDecimalNumbers() {
        assertEquals("Một chấm hai ba", speller.spell(1.23));
        assertEquals("Mười hai chấm ba bốn", speller.spell(12.34));
        assertEquals("Một trăm hai mươi ba chấm bốn năm", speller.spell(123.45));
        assertEquals("Một nghìn hai trăm ba mươi tư chấm năm sáu", speller.spell(1234.56));
        assertEquals("Mười hai nghìn ba trăm bốn mươi lăm chấm sáu bảy", speller.spell(12345.67));
    }

    @Test
    void testComplexNegativeNumbers() {
        assertEquals("Âm một trăm hai mươi ba", speller.spell(-123));
        assertEquals("Âm một nghìn hai trăm ba mươi tư", speller.spell(-1234));
        assertEquals("Âm mười hai nghìn ba trăm bốn mươi lăm", speller.spell(-12345));
        assertEquals("Âm một trăm hai mươi ba nghìn bốn trăm năm mươi sáu", speller.spell(-123456));
        assertEquals("Âm một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell(-1234567));
    }

    @Test
    void testZeroHandling() {
        assertEquals("Không", speller.spell(0));
        assertEquals("Không", speller.spell("0"));
        assertEquals("Không", speller.spell("00"));
        assertEquals("Không", speller.spell("000"));
        assertEquals("Không", speller.spell("0000"));
        assertEquals("Không", speller.spell("00000"));
    }

    @Test
    void testLeadingZeros() {
        assertEquals("Một", speller.spell("0001"));
        assertEquals("Mười", speller.spell("0010"));
        assertEquals("Một trăm", speller.spell("0100"));
        assertEquals("Một nghìn", speller.spell("1000"));
        assertEquals("Mười nghìn", speller.spell("010000"));
    }

    @Test
    void testTrailingZeros() {
        assertEquals("Một", speller.spell("1.0"));
        assertEquals("Một chấm một", speller.spell("1.10"));
        assertEquals("Một chấm một", speller.spell("1.100"));
        assertEquals("Một chấm một", speller.spell("1.1000"));
    }

    @Test
    void testMixedFormatting() {
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell("1,234,567"));
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell("1 234 567"));
        assertEquals("Một triệu hai trăm ba mươi tư nghìn năm trăm sáu mươi bảy", speller.spell("1234567"));
    }
} 