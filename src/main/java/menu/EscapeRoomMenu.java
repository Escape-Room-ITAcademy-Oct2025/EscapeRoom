package menu;

import service.EscapeRoomService;
import utils.InputUtils;
import exception.InvalidDataException;
import exception.OperationFailedException;
import exception.DataNotFoundException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EscapeRoomMenu {

    private final EscapeRoomService escapeRoomService;
    private final Scanner scanner;

    public EscapeRoomMenu(Scanner scanner) {
        this.escapeRoomService = new EscapeRoomService();
        this.scanner = scanner;
    }

    public void start() {
        int option;
        do {
            printMenuOptions();
            option = readOption();

            try {
                switch (option) {
                    case 1 -> createEscapeRoom();
                    case 2 -> listEscapeRooms();
                    case 3 -> deleteEscapeRoom();
                    case 0 -> System.out.println("Returning to main menu...");
                    default -> System.out.println("❌ Invalid option. Try again.");
                }
            } catch (InvalidDataException | OperationFailedException | DataNotFoundException e) {
                System.out.println("⚠️ " + e.getMessage());
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void printMenuOptions() {
        System.out.println("\n=== ESCAPE ROOM MENU ===");
        System.out.println("1. Create new Escape Room");
        System.out.println("2. Show all Escape Rooms");
        System.out.println("3. Delete Escape Room");
        System.out.println("0. Return to Main Menu");
        System.out.print("Select an option: ");
    }

    private int readOption() {
        int option = -1;
        boolean valid = false;
        while (!valid) {
            try {
                option = InputUtils.readInt(scanner);
                if (option < 0 || option > 3) {
                    throw new InputMismatchException("Option must be between 0 and 3.");
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number between 0 and 3.");
                scanner.nextLine();
            }
        }
        return option;
    }

    private void createEscapeRoom() {
        String name = InputUtils.readNonEmptyString(scanner, "Enter the name of the Escape Room: ");
        System.out.println(escapeRoomService.createEscapeRoom(name));
    }

    private void listEscapeRooms() {
        System.out.println(escapeRoomService.listEscapeRooms());
    }

    private void deleteEscapeRoom() {
        System.out.print("Enter the ID of the Escape Room to delete: ");
        int id = InputUtils.readInt(scanner);
        System.out.println(escapeRoomService.deleteEscapeRoomById(id));
    }
}
