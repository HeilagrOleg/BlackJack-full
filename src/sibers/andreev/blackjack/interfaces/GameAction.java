package sibers.andreev.blackjack.interfaces;

import sibers.andreev.blackjack.res.Dealer;
import sibers.andreev.blackjack.res.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class GameAction {

    public static void startRound() {
        Scanner scanner = new Scanner(System.in);
        String roundText = "Игра начинается. Делаем ставки.%n1 - Сделать ставку%n";
        System.out.printf(roundText);
        int choice = scanner.nextInt();
        if (choice == 1) {
            roundText = "Ставки сделаны, начинается раздача карт.%n";
            System.out.printf(roundText);
        } else {
            String fail = "Неверная команда";
            System.out.println(fail);
            startRound();
        }
    }

    public static void getFirsRoundStatus(Player firstPlayer, Dealer dealer, Player secondPlayer, Player thirdPlayer) {
        String firstPlayerCards = "Карты (%s):%n" + firstPlayer.toString() + "%n";
        String dealerCards;
        if (dealer.isFirstRound()) {
            dealerCards = "Карты диллера:%n" + dealer.firstToString() + "%n";
        } else {
            dealerCards = "Карты диллера:%n" + dealer.toString() + "%n";
        }
        String secondPlayerCards = "Карты (%s):%n" + secondPlayer.toString() + "%n";
        String thirdPlayerCards = "Карты (%s):%n" + thirdPlayer.toString() + "%n";
        System.out.printf(firstPlayerCards, firstPlayer.getName());
        System.out.printf(secondPlayerCards, secondPlayer.getName());
        System.out.printf(thirdPlayerCards, thirdPlayer.getName());
        System.out.printf(dealerCards + "----------------%n");
    }

    public static void showWinners(ArrayList<Player> winnersList) {
        String winners = "Победители: ";
        for (Player e : winnersList) {
            winners += e.getName() + "|";
        }
        System.out.println("|" + winners);
    }

    public static void showWinnerDealer(Dealer dealer) {
        System.out.printf("Победил диллер%n" + dealer.toString());
    }

    public static void showBank(int bank) {
        System.out.printf("%nСумма в банке: %d%n", bank);
    }

    public static boolean showNoWinners() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Победителей нет%nПродолжить?%n1 - Да%n2 - Нет%n");
        int choice = scanner.nextInt();
        if (choice == 1) {
            return true;
        } else if (choice == 2) {
            return false;
        } else {
            System.out.printf("Неверная команда");
            showNoWinners();
        }
        return false;
    }

    public static void showDouble(boolean isPoints, Player player) {
        int maxPoints = 21;
        System.out.printf("%n%s: взял карту%n", player.getName());
        System.out.printf(player.toString());
        if (!isPoints) {
            if(player.getPoints()> maxPoints) {
                System.out.printf("%n%s: перебор%n______%n", player.getName());
            } else {
                System.out.printf("%n%s: остановился%n______%n", player.getName());
            }
        }
    }

    public static void showChoicePlayer(boolean choice[], Player bot) {
        if (choice.length != 3) {
            if (choice[1]) {
                if (choice[0]) {
                    System.out.printf("%n%s: взял карту%n", bot.getName());
                    System.out.printf(bot.toString());
                } else if (!bot.isLost()) {
                    System.out.printf("%n%s: остановился%n______%n", bot.getName());
                }
            } else {
                System.out.printf("%n%s: перебор%n______%n", bot.getName());
            }
        }
    }

    public static boolean isSplit() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%nПровести сплитование?%n1 - Да%n2 - Нет%n");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return true;
            case "2":
                return false;
            default:
                System.out.printf("Неверная команда");
                isSplit();
        }
        return false;
    }

    public static boolean isChoiceInsurance() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Застраховаться от БлекДжека диллера?%n1 - Да%n2 - Нет%n");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                return true;
            case "2":
                return false;
            default:
                System.out.printf("Неверная команда");
                isChoiceInsurance();
        }
        return false;
    }

    public static boolean isStatusEndRound(Player firstPlayer, Player secondPlayer, Player thirdPlayer) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%n%s: %d фишек%n%s: %d фишек%n%s: %d фишек%n",
                firstPlayer.getName(),
                firstPlayer.getMoney(),
                secondPlayer.getName(),
                secondPlayer.getMoney(),
                thirdPlayer.getName(),
                thirdPlayer.getMoney());
        System.out.printf("%nПродолжить?%n1 - Да%n2 - Нет%n");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return true;
            case "2":
                return false;
            default:
                System.out.printf("Неверная команда");
        }
        return isStatusEndRound(firstPlayer, secondPlayer, thirdPlayer);
    }

    public static int isChoicePlayer(Player player, boolean[] isChoice) {
        if (isChoice[1]) {
            GameAction.showChoicePlayer(new boolean[]{true, true}, player);
            return 2;
        }
        Scanner scanner = new Scanner(System.in);
        String fail = "Неверная команда";
        String choiceText;
        if (player.isFirstRound()) {
            choiceText = "%n1 - Взять карту%n2 - Хватит%n3 - Дабл%n4 - Сарендо%n";
        } else {
            choiceText = "%n1 - Взять карту%n2 - Хватит%n";
        }
        String cards = "%n%s%n" + player.toString() + choiceText;
        System.out.printf(cards, player.getName());
        String choice = scanner.nextLine();

        if (player.isFirstRound()) {
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    if (!player.isUser()) {
                        System.out.printf("%n%s: остановился%n______%n", player.getName());
                    }
                    return 2;
                case "3":
                    System.out.printf("%n%s: Дабл%n______%n", player.getName());
                    return 3;
                case "4":
                    System.out.printf("%n%s: сдался%n______%n", player.getName());
                    return 3;
                default:
                    System.out.println(fail);
                    isChoicePlayer(player, isChoice);
                    break;
            }
        } else {
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    if (!player.isUser()) {
                        System.out.printf("%n%s: остановился%n______%n", player.getName());
                    }
                    return 2;
                default:
                    System.out.println(fail);
                    isChoicePlayer(player, isChoice);
                    break;
            }
        }
        return 2;
    }
}
