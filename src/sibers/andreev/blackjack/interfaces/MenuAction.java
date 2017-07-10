package sibers.andreev.blackjack.interfaces;

import java.util.Scanner;

public class MenuAction {

    public static boolean showMenu () {
        System.out.printf("Добро пожаловать в БлекДжек%n1 - Начать играть%n2 - Выход%n");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("1")){
            return true;
        }
        if (choice.equals("2")) {
            return false;
        } else {
            System.out.printf("Неверная команда%n%n");
            showMenu();
        }
        return false;
    }

    public static int showPlayerSelection () {
        System.out.printf("Выберите количество игроков (от 1 до 3)%n");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("1")){
            System.out.printf("Выбор сделан %n%n");
            return 1;
        }
        if (choice.equals("2")){
            System.out.printf("Выбор сделан %n%n");
            return 2;
        }
        if (choice.equals("3")){
            System.out.printf("Выбор сделан %n%n");
            return 3;
        } else {
            System.out.printf("Неверная команда%n");
            showPlayerSelection();
        }
        return Integer.parseInt(choice);
    }
   }
