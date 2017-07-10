package sibers.andreev.blackjack.res;

import java.util.ArrayList;

public class Player {


    private int pointsForStop;
    private int pointsForContinuation;
    private int chanceTakeCard;
    private int money;
    private int points;
    private boolean isUser;
    private boolean isLost;
    private boolean isFirstRound;
    private boolean isSplit;
    private String name;

    private ArrayList<Card> cardsOnHand;

    public Player(String name) {
        pointsForStop = 0;
        pointsForContinuation = 0;
        chanceTakeCard = 0;
        cardsOnHand = new ArrayList<>();
        money = 10000;
        this.name = name;
        isUser = true;
        isLost = false;
        isSplit = false;
        this.isFirstRound = true;
    }


    public void setSplit(boolean split) {
        isSplit = split;
    }

    public void setCardsOnHand(ArrayList<Card> cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setFirstRound(boolean firstRound) {
        this.isFirstRound = firstRound;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

    public ArrayList<Card> getCardsOnHand() {
        return cardsOnHand;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getPointsForStop() {
        return pointsForStop;
    }

    public int getPointsForContinuation() {
        return pointsForContinuation;
    }

    public int getChanceTakeCard() {
        return chanceTakeCard;
    }

    public boolean isLost() {
        return isLost;
    }

    public boolean isFirstRound() {
        return isFirstRound;
    }

    public boolean isUser() {
        return isUser;
    }

    public boolean isSplit() {
        return isSplit;
    }

    public String toString() {
        String cardsNames = "";
        for (Card e : getCardsOnHand()) {
            cardsNames += "|" + e.getName() + " " + e.getSuit();
        }
        cardsNames += "|%nОчки: " + getPoints() + "%n";
        return cardsNames;
    }
}
