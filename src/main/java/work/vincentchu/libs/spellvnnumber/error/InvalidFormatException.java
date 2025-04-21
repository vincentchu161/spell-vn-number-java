package work.vincentchu.libs.spellvnnumber.error;

/**
 * Exception thrown when the input format is invalid
 */
public class InvalidFormatException extends SpellException {
    public InvalidFormatException() {
    }

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormatException(Throwable cause) {
        super(cause);
    }
}