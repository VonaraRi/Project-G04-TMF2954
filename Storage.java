import java.util.List;

/**
 * Structural Java Interface fulfilling Member 1's Individual OOP Requirement.
 * Focuses on Abstraction and storage-agnostic data operations.
 * * Creator: Rionnalyn (GUI + Data Storage + Integration)
 * Tester:
 */
public interface Storage {
    /**
     * Saves a user's quiz score to the storage layer.
     * @throws CustomStorageException if a file or database error occurs.
     */
    void saveScore(String username, int score) throws CustomStorageException;

    /**
     * Retrieves all recorded scores formatted for display.
     * @throws CustomStorageException if a file or database error occurs.
     */
    List<String> getAllScores() throws CustomStorageException;
}