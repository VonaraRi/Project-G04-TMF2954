/**
 * Gamification Interface
 * Defines all gamification behaviours
 * Creator: Sabrina Natasha (106199)
 * Tester:Rosfanida ak Benjamin 106171
 */

public interface Gamification {

    Reward generateReward(int score)
            throws GamificationException;

    String getMotivationalMessage(int score)
            throws GamificationException;

    int calculateStars(int score)
            throws GamificationException;
}
