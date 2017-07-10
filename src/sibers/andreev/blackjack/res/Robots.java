package sibers.andreev.blackjack.res;

public class Robots extends Player {

    private int pointsForStop;
    private int pointsForContinuation;
    private int chanceTakeCard;
    private boolean isUser;

    public Robots(String name) {
        super(name);
        pointsForStop = 17 + (int) (Math.random() * 19);
        pointsForContinuation = 10 + (int) (Math.random() * 16);
        chanceTakeCard = 25 + (int) (Math.random() * 75);
        isUser = false;
    }

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
}
