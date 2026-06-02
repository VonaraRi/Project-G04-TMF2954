//Creator: Sabrina Natasha (106199)

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {

    public void sortScores(List<String> records) {

        Collections.sort(records,
                new Comparator<String>() {

                    @Override
                    public int compare(String a, String b) {

                        int scoreA =
                                Integer.parseInt(a.split(",")[1]);

                        int scoreB =
                                Integer.parseInt(b.split(",")[1]);

                        return Integer.compare(scoreB, scoreA);
                    }
                });
    }

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
