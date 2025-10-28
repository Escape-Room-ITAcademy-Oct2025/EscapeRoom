package utils;

import java.util.Scanner;

public class InputUtils {

    private InputUtils() {

    }

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }

    public static double readDouble(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim().replace(",", ".");
                double value = Double.parseDouble(input);

                if (value <= 0) {
                    System.out.print("⚠️ Please enter a positive number: ");
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid input. Please enter a valid decimal number: ");
            }
        }
    }

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
