package sibers.andreev.blackjack.res;

public class Dealer extends Robots {

    private int pointsForStop;
    private int pointsForContinuation;
    private int chanceTakeCard;
    private boolean isFirstRound;
    private boolean isUser;

    public Dealer(String name) {
        super(name);
        this.pointsForStop = 17;
        this.pointsForContinuation = 16;
        this.chanceTakeCard = 100;
        this.isFirstRound = true;
        isUser = false;
    }


    @Override
    public boolean isFirstRound() {
        return isFirstRound;
    }

    @Override
    public void setFirstRound(boolean firstRound) {
        isFirstRound = firstRound;
    }

    @Override
    public boolean isUser() {
        return isUser;
    }


    @Override
    public int getPointsForStop() {
        return pointsForStop;
    }

    @Override
    public int getPointsForContinuation() {
        return pointsForContinuation;
    }

    @Override
    public int getChanceTakeCard() {
        return chanceTakeCard;
    }


    public String firstToString() {

        return  "|" + getCardsOnHand().get(0).getName() + " " + getCardsOnHand().get(0).getSuit() +
                "|(Карта в темную" + ")|%nОчки: " + getCardsOnHand().get(0).getPoints() + "%n";
    }
}
