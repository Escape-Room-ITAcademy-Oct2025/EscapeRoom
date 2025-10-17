package menu;

import model.Room;
import model.Decoration;
import model.Hint;
import service.InventoryService;

import java.util.List;
import java.util.Scanner;

public class InventoryMenu {

    private final InventoryService inventoryService;
    private final Scanner scanner;

    public InventoryMenu() {
        this.inventoryService = new InventoryService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n===== INVENTORY MENU =====");
            System.out.println("1. Show all Rooms");
            System.out.println("2. Show all Decorations");
            System.out.println("3. Show all Hints");
            System.out.println("4. Find Room by ID");
            System.out.println("5. Show Hints by Room ID");
            System.out.println("6. Calculate Total Inventory Value");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();


            switch (option) {
                case 1 -> showAllRooms();
                case 2 -> showAllDecorations();
                case 3 -> showAllHints();
                case 4 -> findRoomById();
                case 5 -> findHintsByRoomId();
                case 6 -> calculateTotalValue();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Try again.");
            }

        } while (option != 0);
    }

    private void showAllRooms() {
        List<Room> rooms = inventoryService.findAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private void showAllDecorations() {
        List<Decoration> decorations = inventoryService.findAllDecorations();
        if (decorations.isEmpty()) {
            System.out.println("No decorations found.");
        } else {
            decorations.forEach(System.out::println);
        }
    }

    private void showAllHints() {
        List<Hint> hints = inventoryService.findAllHints();
        if (hints.isEmpty()) {
            System.out.println("No hints found.");
        } else {
            hints.forEach(System.out::println);
        }
    }

    private void findRoomById() {
        System.out.print("Enter Room ID: ");
        int id = scanner.nextInt();
        Room room = inventoryService.findRoomById(id);
        System.out.println(room != null ? room : "Room not found.");
    }

    private void findHintsByRoomId() {
        System.out.print("Enter Room ID: ");
        int id = scanner.nextInt();
        List<Hint> hints = inventoryService.findHintsByRoom(id);
        if (hints.isEmpty()) {
            System.out.println("No hints for this room.");
        } else {
            hints.forEach(System.out::println);
        }
    }

    private void calculateTotalValue() {
        double total = inventoryService.calculateTotalInventoryValue();
        System.out.printf("Total Inventory Value: %.2f€%n", total);
    }
}
