/**
 * Custom exception for Learning Module content errors.
 *
* This is used when the learning module has invalid content,
 * for example if fewer than 10 learning screens are loaded.
 *
 * OOP concept:
 * This demonstrates custom exception handling.
 *
 * Creator: Chan Ka Hou 103617
 * Tester: Rionnalyn 106148
 */

// Constructor receives a simple error message and adds a clear prefix.
// This makes learning module errors easier to identify during debugging.
public class LearningContentException extends Exception {
    public LearningContentException(String message) {
        super("Learning Content Error: " + message);
    }
}
