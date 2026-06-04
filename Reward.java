/**
 * Stores badge, stars and motivational message.
 * Creator: Sabrina Natasha (106199)
 * Tester:Rosfanida ak Benjamin 106171
 */

public class Reward {

    private String badge;
    private int stars;
    private String motivationalMessage;

    public Reward(String badge,
                  int stars,
                  String motivationalMessage) {

        this.badge = badge;
        this.stars = stars;
        this.motivationalMessage = motivationalMessage;
    }

    public String getBadge() {
        return badge;
    }

    public int getStars() {
        return stars;
    }

    public String getMotivationalMessage() {
        return motivationalMessage;
    }

    @Override
    public String toString() {

        return "Badge: " + badge
                + "\nStars: " + stars
                + "\nMessage: " + motivationalMessage;
    }
}
