package work.vincentchu.libs.spellvnnumber.error;

/**
 * Exception thrown when the input number is invalid
 * Ngoại lệ được ném khi số đầu vào không hợp lệ
 */
public class InvalidNumberException extends SpellException {
    public InvalidNumberException() {
    }

    /**
     * Creates a new invalid number exception with the specified message
     * Tạo một ngoại lệ số không hợp lệ mới với thông báo được chỉ định
     *
     * @param message The exception message (Thông báo ngoại lệ)
     */
    public InvalidNumberException(String message) {
        super(message);
    }

    /**
     * Creates a new invalid number exception with the specified message and cause
     * Tạo một ngoại lệ số không hợp lệ mới với thông báo và nguyên nhân được chỉ định
     *
     * @param message The exception message (Thông báo ngoại lệ)
     * @param cause   The cause of the exception (Nguyên nhân của ngoại lệ)
     */
    public InvalidNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNumberException(Throwable cause) {
        super(cause);
    }
}