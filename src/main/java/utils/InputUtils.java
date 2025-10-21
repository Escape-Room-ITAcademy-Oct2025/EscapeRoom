package utils;

import java.util.Scanner;

/**
 * Utility class for safely reading user input in console menus.
 * All menus should use these methods to ensure consistent validation.
 */
public class InputUtils {

    private InputUtils() {
        // Prevent instantiation
    }

    // ────────────────────────────────
    // 🔢 Read integer
    // ────────────────────────────────
    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid input. Please enter a valid integer: ");
            }
        }
    }

    // ────────────────────────────────
    // 💶 Read double
    // ────────────────────────────────
    public static double readDouble(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim().replace(",", ".");
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid input. Please enter a valid decimal number: ");
            }
        }
    }

    // ────────────────────────────────
    // ✏️ Read non-empty string
    // ────────────────────────────────
    public static String readNonEmptyString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("⚠️ Input cannot be empty. Try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    // ────────────────────────────────
    // ⏸ Pause
    // ────────────────────────────────
    public static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
