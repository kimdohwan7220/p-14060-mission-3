package mission1.view;

import java.util.Scanner;

public class InputView {
    private static final Scanner sc = new Scanner(System.in);

    public static String commandInput() {
        System.out.print("명령) ");
        return sc.nextLine().trim();
    }

    public static String quoteInput() {
        System.out.print("명언 : ");
        return sc.nextLine().trim();
    }

    public static String authorInput() {
        System.out.print("작가 : ");
        return sc.nextLine().trim();
    }

    public static String quoteInput(String existing) {
        System.out.print("명언(기존) : " + existing + "\n명언 : ");
        return sc.nextLine().trim();
    }

    public static String authorInput(String existing) {
        System.out.print("작가(기존) : " + existing + "\n작가 : ");
        return sc.nextLine().trim();
    }
}
