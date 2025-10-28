package menu;

import service.EscapeRoomService;
import utils.InputUtils;
import exception.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EscapeRoomMenu {

    private final EscapeRoomService escapeRoomService;
    private final Scanner scanner;

    public EscapeRoomMenu(Scanner scanner) {
        this.escapeRoomService = EscapeRoomService.getInstance();
        this.scanner = scanner;
    }

    public void start() {
        int option;
        do {
            try {
                printMenuOptions();
                option = InputUtils.readInt(scanner);

                switch (option) {
                    case 1 -> createEscapeRoom();
                    case 2 -> listEscapeRooms();
                    case 3 -> deleteEscapeRoom();
                    case 0 -> System.out.println("Returning to main menu...");
                    default -> System.out.println("❌ Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input. Enter a number.");
                scanner.nextLine();
                option = -1;
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void printMenuOptions() {
        System.out.println("\n=== 🧩 ESCAPE ROOM MENU ===");
        System.out.println("1. ➕ Create new Escape Room");
        System.out.println("2. 📋 Show all Escape Rooms");
        System.out.println("3. 🗑️ Delete Escape Room");
        System.out.println("0. ⬅️ Return to Main Menu");
        System.out.print("Select an option: ");
    }

    private void createEscapeRoom() {
        try {
            System.out.print("Enter the name of the Escape Room: ");
            String name = InputUtils.readNonEmptyString(scanner);

            System.out.println(escapeRoomService.createEscapeRoom(name));

        } catch (InvalidDataException | OperationFailedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error creating escape room.");
        }
    }

    private void listEscapeRooms() {
        try {
            System.out.println(escapeRoomService.listEscapeRooms());
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error listing escape rooms.");
        }
    }

    private void deleteEscapeRoom() {
        try {
            System.out.print("Enter the ID of the Escape Room to delete: ");
            int id = InputUtils.readInt(scanner);

            System.out.println(escapeRoomService.deleteEscapeRoomById(id));

        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid ID. Enter a correct number.");
            scanner.nextLine();
        } catch (DataNotFoundException | OperationFailedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error deleting escape room.");
        }
    }
}
