# Spell Vietnamese Number (Java)

A Java library for converting numbers to their Vietnamese text representation.

## Features

- Convert numbers to Vietnamese text
- Support for integers and decimals
- Support for negative numbers
- Customizable configuration
- Support for different input formats (String, Number)
- Comprehensive test coverage
- Financial Standards Support:
  - Compliant with Vietnamese Accounting Standards (VAS)
  - Financial number formatting with proper unit scaling
- Language Customization:
  - Supports Vietnamese dialects and regional variations
  - Customizable pronunciation and unit names
  - Extensible for new language variants

## Demo & Resources

- **NPM Package:** [spell-vn-number](https://www.npmjs.com/package/spell-vn-number)
- **JavaScript/TypeScript Repository:** [https://github.com/vincentchu161/spell-vn-number](https://github.com/vincentchu161/spell-vn-number)
  - **Demo:** [https://npm-spell-vn-number.vincentchu.work/](https://npm-spell-vn-number.vincentchu.work/)
  - **Demo2:** [github-pages-demo.html](https://vincentchu161.github.io/spell-vn-number/examples/github-pages-demo.html)

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.vincentchu161</groupId>
    <artifactId>spell-vn-number</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Basic Usage

```java
import work.vincentchu.libs.spellvnnumber.Speller;

public class Main {
    public static void main(String[] args) {
        Speller speller = new Speller();

        // Convert numbers to Vietnamese text
        System.out.println(speller.spell(1));        // "Một"
        System.out.println(speller.spell(10));       // "Mười"
        System.out.println(speller.spell(100));      // "Một trăm"
        System.out.println(speller.spell(1000));     // "Một nghìn"
        System.out.println(speller.spell(1000000));  // "Một triệu"
        System.out.println(speller.spell(1000000000)); // "Một tỷ"

        // Decimal numbers
        System.out.println(speller.spell(1.1));      // "Một chấm một"

        // Negative numbers
        System.out.println(speller.spell(-1));       // "Âm một"

        // String input
        System.out.println(speller.spell("1"));      // "Một"
    }
}
```

### Custom Configuration

```java
import work.vincentchu.libs.spellvnnumber.Speller;
import work.vincentchu.libs.spellvnnumber.SpellerConfig;

public class Main {
    public static void main(String[] args) {
        SpellerConfig config = new SpellerConfig();
        config.setCurrencyUnit("đồng");
        config.setCapitalizeInitial(false);
        config.setSeparator(" ");

        Speller speller = new Speller(config);

        System.out.println(speller.spell(1));    // "một đồng"
        System.out.println(speller.spell(1000)); // "một nghìn đồng"
    }
}
```

## Configuration Options

The `SpellerConfig` class provides the following configuration options:

- `separator`: String used to separate words (default: " ")
- `negativeSign`: Character used for negative numbers (default: "-")
- `decimalPoint`: Character used for decimal point (default: ".")
- `thousandSign`: Character used for thousands separator (default: ",")
- `negativeText`: Text for negative numbers (default: "âm")
- `pointText`: Text for decimal point (default: "chấm")
- `capitalizeInitial`: Whether to capitalize the first letter (default: true)
- `currencyUnit`: Currency unit to append (default: "")
- `redundantZeroChar`: Character used for redundant zeros (default: "0")
- `keepOneZeroWhenAllZeros`: Whether to keep one zero when all are zeros (default: false)
- `specificText`: Custom text for specific cases:
  - `oddText`: Text for odd numbers (default: "lẻ")
  - `tenText`: Text for ten (default: "mười")
  - `oneToneText`: Special text for digit 1 (default: "mốt")
  - `fourToneText`: Special text for digit 4 (default: "tư")
  - `fiveToneText`: Special text for digit 5 (default: "lăm")
- `digitNames`: Custom names for digits (default: standard Vietnamese names)
- `unitNames`: Custom names for units (default: standard Vietnamese units)

## Error Handling

The library throws the following exceptions:

- `InvalidFormatException`: When the input format is invalid (null, empty, unsupported type)
- `InvalidNumberException`: When the number format is invalid (contains invalid characters, multiple decimal points)

## Development

### Building

```bash
mvn clean install
```

### Testing

```bash
mvn test
```

## Author

- **Name:** vincentchu
- **Email:** contact@vincentchu.work
- **Website:** [https://resume.vincentchu.work/](https://resume.vincentchu.work/)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details. 