/**
 * Custom exception for Member 2 Learning Module content errors.
 *
 * Creator: Member 2
 * Tester:
 */
public class LearningContentException extends Exception {
    public LearningContentException(String message) {
        super("Learning Content Error: " + message);
    }
}
