//Creator: Sabrina Natasha (106199)
import java.util.List;

/**
 * Handles leaderboard sorting and medal assignment.
 */
public class Leaderboard {

    /**
     * Sorts score records in descending order using Selection Sort.
     * Format of each record: Username,Score
     */
    public void sortScores(List<String> records) {

        // Move through each position in the list
        for (int i = 0; i < records.size() - 1; i++) {

            // Assume current record has the highest score
            int maxIndex = i;

            // Search remaining records for a higher score
            for (int j = i + 1; j < records.size(); j++) {

                // Extract score from current highest record
                int score1 =
                        Integer.parseInt(
                                records.get(maxIndex)
                                       .split(",")[1]);

                // Extract score from record being compared
                int score2 =
                        Integer.parseInt(
                                records.get(j)
                                       .split(",")[1]);

                // Update maxIndex if a higher score is found
                if (score2 > score1) {
                    maxIndex = j;
                }
            }

            // Swap current position with highest score found
            String temp = records.get(i);
            records.set(i, records.get(maxIndex));
            records.set(maxIndex, temp);
        }
    }

    /**
     * Returns medal icons for top leaderboard positions.
     */
    public String getMedal(int rank) {

        switch(rank) {

            case 1:
                return "🥇";

            case 2:
                return "🥈";

            case 3:
                return "🥉";

            default:
                return "";
        }
    }
}
