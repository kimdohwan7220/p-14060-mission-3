package mission1.view;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InputView {
    private static final Scanner sc = new Scanner(System.in);

    public static Scanner genScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    private static String input() {
        return sc.nextLine().trim();
    }

    public static String commandInput() {
        System.out.print("명령) ");
        return input();
    }

    public static String quoteInput() {
        System.out.print("명언 : ");
        return input();
    }

    public static String authorInput() {
        System.out.print("작가 : ");
        return input();
    }

    public static String quoteInput(String existing) {
        System.out.print("명언(기존) : " + existing + "\n명언 : ");
        return input();
    }

    public static String authorInput(String existing) {
        System.out.print("작가(기존) : " + existing + "\n작가 : ");
        return input();
    }
}
