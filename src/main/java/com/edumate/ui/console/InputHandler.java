package com.edumate.ui.console;

import java.util.Scanner;

public class InputHandler {

    private static Scanner scanner;

    public static String read() {
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }
}
