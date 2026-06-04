/**
 * Custom exception handling for storage-related failures (CSV and DB).
 * Satisfies the requirement for custom, robust exception usage.
 * Creator: Rionnalyn 106148
 * Tester:
 */
public class CustomStorageException extends Exception {

    public CustomStorageException(String message, Throwable cause) {
        super("Storage Error: " + message, cause);
    }
}
