package sibers.andreev.blackjack.control;

import sibers.andreev.blackjack.control.res.CardsControl;
import sibers.andreev.blackjack.control.res.WinnersControl;
import sibers.andreev.blackjack.interfaces.GameAction;
import sibers.andreev.blackjack.res.Card;
import sibers.andreev.blackjack.res.Dealer;
import sibers.andreev.blackjack.res.Deck;
import sibers.andreev.blackjack.res.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static sibers.andreev.blackjack.control.res.CardsControl.giveCard;
import static sibers.andreev.blackjack.control.res.CardsControl.isGiveCardBots;

public class GameControl {

    private int bet;
    private int moneyInBank;
    private ArrayList<Card> gameDeck;
    private ArrayList<Player> insuranceList;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;
    private Dealer dealer;

    public GameControl(Player firstPlayer, Player secondPlayer, Player thirdPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.thirdPlayer = thirdPlayer;
        this.dealer = new Dealer("Диллер");
        gameDeck = new Deck().createDeck();
        insuranceList = new ArrayList<>();
        bet = 1000;
    }

    private void setGameDeck(ArrayList<Card> gameDeck) {
        this.gameDeck = gameDeck;
    }

    public void playRound() {

        clearPlayersAndDeck(new ArrayList<>(Arrays.asList(firstPlayer, secondPlayer, thirdPlayer, dealer)), gameDeck);

        GameAction.startRound();
        placeBet(dealer, firstPlayer, secondPlayer, thirdPlayer);

        CardsControl.giveFirsCards(dealer, gameDeck);
        CardsControl.giveFirsCards(firstPlayer, gameDeck);
        CardsControl.giveFirsCards(secondPlayer, gameDeck);
        CardsControl.giveFirsCards(thirdPlayer, gameDeck);

        GameAction.showBank(moneyInBank);

        dealer.setFirstRound(true);
        GameAction.getFirsRoundStatus(firstPlayer, dealer, secondPlayer, thirdPlayer);
        dealer.setFirstRound(false);

        getInsurance(firstPlayer, dealer);
        getInsurance(secondPlayer, dealer);
        getInsurance(thirdPlayer, dealer);

        ArrayList<Player> winnerList = WinnersControl.getWinnersListBlackJack(firstPlayer, secondPlayer, thirdPlayer, dealer);

        getWinners(winnerList, firstPlayer, secondPlayer, thirdPlayer, dealer, false);

        getSplit(firstPlayer);

        getAction(firstPlayer, gameDeck);
        getAction(secondPlayer, gameDeck);
        getAction(thirdPlayer, gameDeck);
        getAction(dealer, gameDeck);

        GameAction.getFirsRoundStatus(firstPlayer, dealer, secondPlayer, thirdPlayer);

        winnerList.clear();
        winnerList = WinnersControl.getFinalList(firstPlayer, secondPlayer, thirdPlayer, dealer);

        getWinners(winnerList, firstPlayer, secondPlayer, thirdPlayer, dealer, true);
    }

    private void placeBet(Dealer dealer, Player firstPlayer, Player secondPlayer, Player thirdPlayer) {
        int betCounter = 0;
        secondPlayer.setMoney(secondPlayer.getMoney() - bet);
        betCounter++;
        thirdPlayer.setMoney(thirdPlayer.getMoney() - bet);
        betCounter++;
        dealer.setMoney(dealer.getMoney() - bet);
        betCounter++;
        firstPlayer.setMoney(firstPlayer.getMoney() - bet);
        betCounter++;
        moneyInBank = bet * betCounter;
    }

    private void getAction(Player player, ArrayList<Card> deck) {
        if (player.isUser() && !player.isSplit()) {
            int isChoice = GameAction.isChoicePlayer(player, new boolean[]{true, false});
            player.setFirstRound(false);
            if (isChoice == 4) {
                getSurrender(player);
            }
            while (isChoice == 1) {
                isChoice = GameAction.isChoicePlayer(player, new boolean[]{true, giveCard(player, deck)});
            }

            if (isChoice == 3) {
                moneyInBank += (bet*2);
                player.setMoney(player.getMoney() - bet);
                GameAction.showDouble(giveCard(player, deck), player);
            }
        } else {
            while (true) {
                boolean[] isChoiceBot = isGiveCardBots(player, deck);
                if (isChoiceBot[0]) {
                    GameAction.showChoicePlayer(isChoiceBot, player);
                } else {
                    GameAction.showChoicePlayer(isChoiceBot, player);
                    return;
                }
            }
        }
    }

    private void getWinners(ArrayList<Player> winnerList, Player firstPlayer, Player secondPlayer,
                            Player thirdPlayer, Dealer dealer, boolean isFinal) {
        if (winnerList.size() > 0) {
            if (winnerList.get(0).equals(dealer)) {
                for (Player e : insuranceList) {
                    e.setMoney(e.getMoney() + bet);
                }
                moneyInBank = 0;
                GameAction.showWinnerDealer(dealer);
                if (GameAction.isStatusEndRound(firstPlayer, secondPlayer, thirdPlayer)) {
                    playRound();
                } else {
                    System.exit(0);
                }
            } else {
                int prize = moneyInBank / winnerList.size();
                moneyInBank = 0;
                for (Player e : winnerList) {
                    e.setMoney(e.getMoney() + prize);
                }
                GameAction.showWinners(winnerList);
            }
            if (GameAction.isStatusEndRound(firstPlayer, secondPlayer, thirdPlayer)) {
                playRound();
            } else {
                System.exit(0);
            }
        } else if (isFinal) {
            if (GameAction.showNoWinners()) {
                playRound();
            } else {
                System.exit(0);
            }
        }
    }

    private void getSurrender(Player player) {
        moneyInBank -= bet / 2;
        player.setMoney(player.getMoney() + bet / 2);
        player.setLost(true);
    }

    private boolean getInsurance(Player player, Dealer dealer) {

        int acePoints = 11;

        if (dealer.getCardsOnHand().get(0).getPoints() == acePoints && player.isUser() && GameAction.isChoiceInsurance()) {
            moneyInBank += bet / 2;
            player.setMoney(player.getMoney() - bet / 2);
            insuranceList.add(player);
        }
        return false;
    }

    private void getSplit(Player player) {
        if (player.getCardsOnHand().get(0).getPoints() == player.getCardsOnHand().get(1).getPoints()) {
            if (player.isUser() && GameAction.isSplit()) {
                player.setSplit(true);
                Player firstHand = new Player(player.getName() + ": Первая рука");
                firstHand.getCardsOnHand().add(player.getCardsOnHand().get(0));
                firstHand.setPoints(player.getCardsOnHand().get(0).getPoints());
                Player secondHand = new Player(player.getName() + ": Вторая рука");
                secondHand.getCardsOnHand().add(player.getCardsOnHand().get(1));
                secondHand.setPoints(player.getCardsOnHand().get(1).getPoints());
                getAction(firstHand, gameDeck);
                getAction(secondHand, gameDeck);
                if (firstHand.getPoints() >= secondHand.getPoints()) {
                    player.setCardsOnHand(firstHand.getCardsOnHand());
                    player.setPoints(firstHand.getPoints());
                } else {
                    player.setCardsOnHand(secondHand.getCardsOnHand());
                    player.setPoints(secondHand.getPoints());
                }
            }
        }
    }

    private void clearPlayersAndDeck(ArrayList<Player> playersList, ArrayList<Card> deck) {
        for (Player e : playersList) {
            e.getCardsOnHand().clear();
            e.setPoints(0);
            e.setLost(false);
            e.setFirstRound(true);
            e.setSplit(false);
        }

        if (deck.size() <= 14) {
            setGameDeck(new Deck().createDeck());
        }
    }
}
