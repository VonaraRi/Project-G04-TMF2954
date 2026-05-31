/**
 * Custom exception for Learning Module content errors.
 *
 * Creator: Chan Ka Hou 103617
 * Tester:
 */

public class LearningContentException extends Exception {
    public LearningContentException(String message) {
        super("Learning Content Error: " + message);
    }
}
