package menu;

import model.EscapeRoom;
import service.EscapeRoomService;

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
            System.out.println("\n===  ESCAPE ROOM MENU ===");
            System.out.println("1. Create new Escape Room");
            System.out.println("2. Show all Escape Rooms");
            System.out.println("3. Delete Escape Room");
            System.out.println("0. Return to Main Menu");
            System.out.print("Select an option: ");

            option = readInt();
            switch (option) {
                case 1 -> createEscapeRoom();
                case 2 -> listEscapeRooms();
                case 3 -> deleteEscapeRoom();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("❌ Invalid option. Try again.");
            }

        } while (option != 0);
    }

    private void createEscapeRoom() {
        System.out.print("Enter the name of the Escape Room: ");
        String name = scanner.nextLine();

        EscapeRoom escapeRoom = new EscapeRoom(name);
        escapeRoomService.createEscapeRoom(escapeRoom);
        System.out.println("✅ Escape Room created successfully: " + escapeRoom);
    }

    private void listEscapeRooms() {
        System.out.println("\n=== LIST OF ESCAPE ROOMS ===");
        escapeRoomService.findAllEscapeRooms().forEach(System.out::println);
    }

    private void deleteEscapeRoom() {
        System.out.print("Enter the ID of the Escape Room to delete: ");
        int id = readInt();

        EscapeRoom er = escapeRoomService.findEscapeRoomById(id);
        if (er != null) {
            escapeRoomService.deleteEscapeRoom(er);
            System.out.println("Escape Room deleted successfully.");
        } else {
            System.out.println("❌ Escape Room not found.");
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid number, please enter an integer: ");
            }
        }
    }
}
