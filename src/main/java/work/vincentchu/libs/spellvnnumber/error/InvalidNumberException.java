package work.vincentchu.libs.spellvnnumber.error;

/**
 * Exception thrown when the number format is invalid
 */
public class InvalidNumberException extends SpellException {
    public InvalidNumberException() {
    }

    public InvalidNumberException(String message) {
        super(message);
    }

    public InvalidNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNumberException(Throwable cause) {
        super(cause);
    }
}