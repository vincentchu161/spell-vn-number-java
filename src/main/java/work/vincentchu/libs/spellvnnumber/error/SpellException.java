package work.vincentchu.libs.spellvnnumber.error;

public class SpellException extends RuntimeException {

    public SpellException() {
    }

    public SpellException(String message) {
        super(message);
    }

    public SpellException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpellException(Throwable cause) {
        super(cause);
    }
}
