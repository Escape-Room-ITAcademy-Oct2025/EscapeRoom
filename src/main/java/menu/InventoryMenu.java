package menu;

import model.*;
import service.InventoryService;
import utils.InputUtils;

import java.util.Scanner;

public class InventoryMenu {

    private final InventoryService inventoryService;
    private final Scanner scanner;

    public InventoryMenu(Scanner scanner) {
        this.inventoryService = new InventoryService();
        this.scanner = scanner;
    }

    public void start() {
        int option;
        do {
            printMenu();
            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> addRoom();
                case 2 -> addHint();
                case 3 -> addDecoration();
                case 4 -> showInventory();
                case 5 -> showTotalValue();
                case 6 -> deleteEntity("Room");
                case 7 -> deleteEntity("Hint");
                case 8 -> deleteEntity("Decoration");
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("❌ Invalid option. Try again.");
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void printMenu() {
        System.out.println("\n=== 🧱 INVENTORY MENU ===");
        System.out.println("1. ➕ Add new Room");
        System.out.println("2. ➕ Add new Hint");
        System.out.println("3. ➕ Add new Decoration");
        System.out.println("4. 📋 Show full Inventory");
        System.out.println("5. 💰 Show total Inventory value");
        System.out.println("6. 🗑️ Delete Room");
        System.out.println("7. 🗑️ Delete Hint");
        System.out.println("8. 🗑️ Delete Decoration");
        System.out.println("0. ⬅️ Return to Admin Menu");
        System.out.print("Select an option: ");
    }

    private void addRoom() {
        System.out.print("Enter room name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter difficulty (EASY, MEDIUM, HARD): ");
        Difficulty difficulty;
        try {
            difficulty = Difficulty.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid difficulty! Defaulting to EASY.");
            difficulty = Difficulty.EASY;
        }

        System.out.print("Enter room price (€): ");
        double price = InputUtils.readDouble(scanner);

        Room room = new Room(name, difficulty, price);
        inventoryService.saveRoom(room);
        System.out.println("✅ Room added successfully: " + room.getName());
    }

    private void addHint() {
        System.out.print("Enter hint description: ");
        String description = scanner.nextLine();

        System.out.print("Enter hint theme: ");
        String theme = scanner.nextLine();

        System.out.print("Enter related Room ID: ");
        int roomId = InputUtils.readInt(scanner);

        System.out.print("Enter hint price (€): ");
        double price = InputUtils.readDouble(scanner);

        Hint hint = new Hint(description, theme, roomId, price);
        inventoryService.saveHint(hint);
        System.out.println("✅ Hint added successfully.");
    }

    private void addDecoration() {
        System.out.print("Enter decoration name: ");
        String name = scanner.nextLine();

        System.out.print("Enter decoration material: ");
        String material = scanner.nextLine();

        System.out.print("Enter related Room ID: ");
        int roomId = InputUtils.readInt(scanner);

        System.out.print("Enter decoration price (€): ");
        double price = InputUtils.readDouble(scanner);

        Decoration decoration = new Decoration(name, material, price, roomId);
        inventoryService.saveDecoration(decoration);
        System.out.println("✅ Decoration added successfully.");
    }

    private void showInventory() {
        System.out.println("\n=== 🧩 ROOMS ===");
        inventoryService.findAllRooms().forEach(System.out::println);

        System.out.println("\n=== 💡 HINTS ===");
        inventoryService.findAllHints().forEach(System.out::println);

        System.out.println("\n=== 🎨 DECORATIONS ===");
        inventoryService.findAllDecorations().forEach(System.out::println);
    }

    private void showTotalValue() {
        double total = inventoryService.calculateTotalInventoryValue();
        System.out.printf("💰 Total Inventory Value: %.2f €%n", total);
    }

    private void deleteEntity(String type) {
        System.out.printf("Enter the %s ID to delete: ", type);
        int id = InputUtils.readInt(scanner);

        boolean deleted = switch (type) {
            case "Room" -> inventoryService.removeRoomById(id);
            case "Hint" -> inventoryService.removeHintById(id);
            case "Decoration" -> inventoryService.removeDecorationById(id);
            default -> false;
        };

        if (deleted)
            System.out.printf("🗑️ %s deleted successfully.%n", type);
        else
            System.out.printf("❌ %s not found.%n", type);
    }
}
