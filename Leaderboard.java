//Creator: Sabrina Natasha (106199)

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {

    public void sortScores(List<String> records) {

    for (int i = 0; i < records.size() - 1; i++) {

        int maxIndex = i;

        for (int j = i + 1; j < records.size(); j++) {

            int score1 =
                    Integer.parseInt(records.get(maxIndex).split(",")[1]);

            int score2 =
                    Integer.parseInt(records.get(j).split(",")[1]);

            if (score2 > score1) {
                maxIndex = j;
            }
        }

        String temp = records.get(i);
        records.set(i, records.get(maxIndex));
        records.set(maxIndex, temp);
    }
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
