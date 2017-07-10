package sibers.andreev.blackjack.control.res;

import sibers.andreev.blackjack.res.Dealer;
import sibers.andreev.blackjack.res.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class WinnersControl {

    public static ArrayList<Player> getWinnersListBlackJack(Player firstPlayer, Player secondPlayer, Player thirdPlayer, Dealer dealer) {
        int blackJack = 21;
        ArrayList<Player> winnersList = new ArrayList<>();

        if (firstPlayer.getPoints() == blackJack) {
            winnersList.add(firstPlayer);
        }
        if (secondPlayer.getPoints() == blackJack) {
            winnersList.add(secondPlayer);
        }
        if (thirdPlayer.getPoints() == blackJack) {
            winnersList.add(thirdPlayer);
        } else if (dealer.getPoints() == blackJack && firstPlayer.getPoints() != blackJack
                && secondPlayer.getPoints() != blackJack) {
            winnersList.add(dealer);
        }
        return winnersList;
    }

    public static ArrayList<Player> getFinalList(Player firstPlayer, Player secondPlayer, Player thirdPlayer, Dealer dealer) {
        int maxPoints = 21;
        ArrayList<Player> winnerList = new ArrayList<>();
        ArrayList<Player> testWinnersList = new ArrayList<>(Arrays.asList(firstPlayer, secondPlayer, thirdPlayer));
        boolean isDealerWon = true;
        if (dealer.getPoints() <= maxPoints) {
            for (Player e : testWinnersList) {
                if (e.getPoints() <= maxPoints) {
                    if (e.getPoints() >= dealer.getPoints()) {
                        isDealerWon = false;
                    }
                }
            }
        } else {
            isDealerWon = false;
        }
        if (isDealerWon) {
            winnerList.add(dealer);
            return winnerList;
        }
        int max = 0;
        for (Player e : testWinnersList) {
            if (e.getPoints() <= maxPoints) {
                if (e.getPoints() > max && !e.isLost()) {
                    winnerList.clear();
                    winnerList.add(e);
                    max = e.getPoints();
                } else if (e.getPoints() == max && !e.isLost()) {
                    // isLost - когда сдался игрок
                    winnerList.add(e);
                }
            }
        }
        return winnerList;
    }
}
