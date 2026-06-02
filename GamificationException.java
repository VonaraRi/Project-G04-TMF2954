/**
 * Custom Exception for Gamification Module
 * Creator: Sabrina Natasha (106199)
 * Tester:
 */

public class GamificationException extends Exception {

    public GamificationException(String message) {
        super("Gamification Error: " + message);
    }
}
