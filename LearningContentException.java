/**
 * Custom exception for Learning Module content errors.
 *
 * Creator: Chan Ka Hou 103617
 * Tester: Rionnalyn 106148
 */

public class LearningContentException extends Exception {
    public LearningContentException(String message) {
        super("Learning Content Error: " + message);
    }
}
