package work.vincentchu.libs.spellvnnumber.error;

/**
 * Base exception class for all spelling-related exceptions
 * Lớp ngoại lệ cơ sở cho tất cả các ngoại lệ liên quan đến việc đọc số
 */
public class SpellException extends RuntimeException {

    public SpellException() {
    }

    /**
     * Creates a new spell exception with the specified message
     * Tạo một ngoại lệ đọc số mới với thông báo được chỉ định
     *
     * @param message The exception message (Thông báo ngoại lệ)
     */
    public SpellException(String message) {
        super(message);
    }

    /**
     * Creates a new spell exception with the specified message and cause
     * Tạo một ngoại lệ đọc số mới với thông báo và nguyên nhân được chỉ định
     *
     * @param message The exception message (Thông báo ngoại lệ)
     * @param cause   The cause of the exception (Nguyên nhân của ngoại lệ)
     */
    public SpellException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpellException(Throwable cause) {
        super(cause);
    }
}
