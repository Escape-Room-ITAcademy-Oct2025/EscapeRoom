package menu;

import model.EscapeRoom;
import service.EscapeRoomService;
import utils.InputUtils;
import exception.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
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
        System.out.println("\n=== 🗑️ DELETE ESCAPE ROOM ===");

        List<EscapeRoom> escapeRooms;
        try {
            escapeRooms = escapeRoomService.findAllEscapeRooms();
        } catch (DataNotFoundException e) {
            System.out.println("⚠️ No Escape Rooms available to delete.");
            return;
        }

        System.out.println("\n=== Available Escape Rooms ===");
        escapeRooms.forEach(er ->
                System.out.printf("ID: %d | Name: %s%n", er.getId(), er.getName())
        );

        try {
            System.out.print("\nEnter Escape Room ID to delete: ");
            int id = InputUtils.readInt(scanner);

            Optional<EscapeRoom> erOpt = escapeRooms.stream()
                    .filter(er -> er.getId() == id)
                    .findFirst();

            if (erOpt.isEmpty()) {
                System.out.println("❌ Invalid Escape Room ID. Operation cancelled.");
                return;
            }

            EscapeRoom escapeRoom = erOpt.get();
            System.out.printf(
                    "⚠️ Are you sure you want to delete '%s'? This will also remove its associated rooms and items. (yes/no): ",
                    escapeRoom.getName()
            );
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (!(confirm.equals("yes"))) {
                System.out.println("❎ Deletion cancelled.");
                return;
            }

            System.out.println(escapeRoomService.deleteEscapeRoomById(id));

        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid ID format.");
            scanner.nextLine();
        } catch (DataNotFoundException | OperationFailedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error deleting Escape Room.");
        }
    }

}
