package sibers.andreev.blackjack.control.res;

import sibers.andreev.blackjack.res.Card;
import sibers.andreev.blackjack.res.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CardsControl {

    public static boolean giveCard(Player player, ArrayList<Card> deck) {
        int victoryPoints = 21;
        player.getCardsOnHand().add(deck.get(deck.size() - 1));
        deck.remove(deck.size() - 1);
        checkAce(player);
        return !(player.getPoints() <= victoryPoints);
    }

    public static void giveFirsCards(Player player, ArrayList<Card> deck) {
        player.getCardsOnHand().add(deck.get(deck.size() - 1));
        deck.remove(deck.size() - 1);
        player.getCardsOnHand().add(deck.get(deck.size() - 1));
        deck.remove(deck.size() - 1);
        checkAce(player);
    }

    public static boolean[] isGiveCardBots(Player player, ArrayList<Card> deck) {
        int maxPoints = 21;
        if (player.getPoints() > maxPoints) {
            return new boolean[]{false, false};
        }
        if (player.getPoints() < player.getPointsForContinuation()) {
            player.getCardsOnHand().add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
            checkAce(player);
            return new boolean[]{true, true};
        } else if (player.getPoints() >= player.getPointsForStop() || player.getPoints()==maxPoints) {
            return new boolean[]{false, true};
        } else {
            int chance = (int) (Math.random() * 100);
            if (player.getChanceTakeCard() <= chance) {
                player.getCardsOnHand().add(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
                checkAce(player);
                return new boolean[]{true, true};
            } else {
                return new boolean[]{false, true};
            }
        }
    }

    private static void checkAce(Player player) {
        int points = 0;
        for (Card e : player.getCardsOnHand()) {
            points += e.getPoints();
        }
        player.setPoints(points);
        ArrayList<Integer> aceList = new ArrayList<>(Arrays.asList(1, 40, 14, 27));
        for (Card e : player.getCardsOnHand()) {
            if (aceList.contains(e.getNumber())) {
                if (player.getPoints() > 21) {
                    player.setPoints(player.getPoints() - 10);
                }
            }
        }
    }
}
