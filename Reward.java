/**
 * Stores badge, stars and motivational message.
 * Creator: Sabrina Natasha (106199)
 * Tester:Rosfanida ak Benjamin 106171
 */

public class Reward {

    /** User's badge achievement. */
    private String badge;

    /** Number of stars earned. */
    private int stars;

    /** Motivational message shown to the user. */
    private String motivationalMessage;

    /**
     * Constructor to initialize reward details.
     */
    public Reward(String badge,
                  int stars,
                  String motivationalMessage) {

        this.badge = badge;
        this.stars = stars;
        this.motivationalMessage = motivationalMessage;
    }

    /**
     * Returns the badge earned.
     */
    public String getBadge() {
        return badge;
    }

    /**
     * Returns the number of stars earned.
     */
    public int getStars() {
        return stars;
    }

    /**
     * Returns the motivational message.
     */
    public String getMotivationalMessage() {
        return motivationalMessage;
    }

    /**
     * Returns reward information as formatted text.
     */
    @Override
    public String toString() {

        return "Badge: " + badge
                + "\nStars: " + stars
                + "\nMessage: " + motivationalMessage;
    }
}
