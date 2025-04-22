package work.vincentchu.libs.spellvnnumber.error;

/**
 * Exception thrown when the input format is invalid
 * Ngoại lệ được ném khi định dạng đầu vào không hợp lệ
 */
public class InvalidFormatException extends SpellException {
    public InvalidFormatException() {
    }

    /**
     * Creates a new invalid format exception with the specified message
     * Tạo một ngoại lệ định dạng không hợp lệ mới với thông báo được chỉ định
     *
     * @param message The exception message (Thông báo ngoại lệ)
     */
    public InvalidFormatException(String message) {
        super(message);
    }

    /**
     * Creates a new invalid format exception with the specified message and cause
     * Tạo một ngoại lệ định dạng không hợp lệ mới với thông báo và nguyên nhân được chỉ định
     *
     * @param message The exception message (Thông báo ngoại lệ)
     * @param cause   The cause of the exception (Nguyên nhân của ngoại lệ)
     */
    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormatException(Throwable cause) {
        super(cause);
    }
}