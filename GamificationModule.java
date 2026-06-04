/**
 * Handles: Reward generation, Badge assignmet, Star calculatiom, Motivational messages
 * Creator: Sabrina Natasha (106199)
 * Tester:Rosfanida ak Benjamin 106171
 */

public class GamificationModule implements Gamification {

    private static final int MAX_SCORE = 20;

    @Override
    public Reward generateReward(int score)
            throws GamificationException {

        validateScore(score);

        String badge;
        int stars;
        String message;

        if (score >= 18) {

            badge = "Gold Badge";
            stars = 5;
            message = "Outstanding";

        } else if (score >= 15) {

            badge = "Silver Badge";
            stars = 4;
            message = "That's good";

        } else if (score >= 10) {

            badge = "Bronze Badge";
            stars = 3;
            message = "Good try";

        } else if (score >= 5) {

            badge = "Participation Badge";
            stars = 2;
            message = "You can do better";

        } else {

            badge = "Participation Badge";
            stars = 1;
            message = "Don't give up";
        }

        return new Reward(
                badge,
                stars,
                message
        );
    }

    @Override
    public String getMotivationalMessage(int score)
            throws GamificationException {

        validateScore(score);

        if (score >= 18)
            return "Outstanding";

        if (score >= 15)
            return "That's good";

        if (score >= 10)
            return "Good try";

        if (score >= 5)
            return "You can do better";

        return "Don't give up";
    }

    @Override
    public int calculateStars(int score)
            throws GamificationException {

        validateScore(score);

        if (score >= 18)
            return 5;

        if (score >= 15)
            return 4;

        if (score >= 10)
            return 3;

        if (score >= 5)
            return 2;

        return 1;
    }

    /**
     * Assign badge only.
     */
    public String getBadge(int score)
            throws GamificationException {

        validateScore(score);

        if (score >= 18)
            return "Gold Badge";

        if (score >= 15)
            return "Silver Badge";

        if (score >= 10)
            return "Bronze Badge";

        return "Participation Badge";
    }

    /**
     * Creates a visual star display.
     */
    public String getStarDisplay(int score)
            throws GamificationException {

        int stars = calculateStars(score);

        StringBuilder display =
                new StringBuilder();

        for (int i = 0; i < stars; i++) {
            display.append("★");
        }

        return display.toString();
    }

    /**
     * Validation method.
     */
    private void validateScore(int score)
            throws GamificationException {

        if (score < 0) {
            throw new GamificationException(
                    "Score cannot be negative."
            );
        }

        if (score > MAX_SCORE) {
            throw new GamificationException(
                    "Score cannot exceed "
                            + MAX_SCORE + "."
            );
        }
    }
}
