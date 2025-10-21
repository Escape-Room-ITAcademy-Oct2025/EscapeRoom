package utils;

import java.util.Scanner;

public class InputUtils {
    public static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print(" Invalid number, please enter an integer: ");
            }

        }
    }

    public static double readDouble(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, please enter a Double: ");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            } else {
                System.out.println("Input cannot be Empty!");
            }
        }
    }
}
