package menu;

import model.EscapeRoom;
import service.EscapeRoomService;
import utils.InputUtils;

import java.util.Optional;
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
            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> createEscapeRoom();
                case 2 -> listEscapeRooms();
                case 3 -> deleteEscapeRoom();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("❌ Invalid option. Try again.");
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

    private void createEscapeRoom() {
        System.out.print("Enter the name of the Escape Room: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        EscapeRoom escapeRoom = new EscapeRoom(name);
        boolean success = escapeRoomService.createEscapeRoom(escapeRoom);

        if (success)
            System.out.println("✅ Escape Room created successfully: " + escapeRoom);
        else
            System.out.println("❌ Error creating Escape Room. Please try again.");
    }

    private void listEscapeRooms() {
        System.out.println("\n=== LIST OF ESCAPE ROOMS ===");
        var escapeRooms = escapeRoomService.findAllEscapeRooms();

        if (escapeRooms.isEmpty()) {
            System.out.println("No Escape Rooms found.");
            return;
        }

        escapeRooms.forEach(System.out::println);
    }

    private void deleteEscapeRoom() {
        System.out.print("Enter the ID of the Escape Room to delete: ");
        int id = InputUtils.readInt(scanner);

        Optional<EscapeRoom> maybeRoom = escapeRoomService.findEscapeRoomById(id);

        if (maybeRoom.isPresent()) {
            EscapeRoom er = maybeRoom.get();
            boolean deleted = escapeRoomService.deleteEscapeRoom(er);

            if (deleted)
                System.out.println("✅ Escape Room deleted successfully: " + er.getName());
            else
                System.out.println("❌ Error deleting Escape Room. Try again.");
        } else {
            System.out.println("No Escape Room found with ID " + id);
        }
    }
}
