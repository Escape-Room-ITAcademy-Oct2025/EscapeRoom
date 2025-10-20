package menu;

import model.Difficulty;
import model.Room;
import model.Hint;
import model.Decoration;
import service.InventoryService;

import java.util.Scanner;

/**
 * InventoryMenu allows the admin to manage the Escape Room inventory.
 * It includes CRUD operations for Rooms, Hints, and Decorations,
 * and displays the total value of the current inventory.
 */
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
            System.out.println("\n=== 🧱 INVENTORY MENU ===");
            System.out.println("1. Add new Room");
            System.out.println("2. Add new Hint");
            System.out.println("3. Add new Decoration");
            System.out.println("4. Show full Inventory");
            System.out.println("5. Show total Inventory value");
            System.out.println("6. Delete Room");
            System.out.println("7. Delete Hint");
            System.out.println("8. Delete Decoration");
            System.out.println("0. Return to Main Menu");
            System.out.print("Select an option: ");

            option = readInt();
            switch (option) {
                case 1 -> addRoom();
                case 2 -> addHint();
                case 3 -> addDecoration();
                case 4 -> showInventory();
                case 5 -> showTotalValue();
                case 6 -> deleteRoom();
                case 7 -> deleteHint();
                case 8 -> deleteDecoration();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("❌ Invalid option. Please try again.");
            }

        } while (option != 0);
    }

    // ------------------- CREATE -------------------

    private void addRoom() {
        System.out.print("Enter room name: ");
        String name = scanner.nextLine();

        System.out.print("Enter difficulty (EASY, MEDIUM, HARD): ");
        String input = scanner.nextLine().trim().toUpperCase();

        Difficulty difficulty;
        try {
            difficulty = Difficulty.valueOf(input); // 👈 aquí conviertes a enum
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Invalid difficulty! Defaulting to EASY.");
            difficulty = Difficulty.EASY; // valor por defecto si el usuario falla
        }

        System.out.print("Enter room price (€): ");
        double price = readDouble();

        Room room = new Room(name, difficulty, price); // ✅ ya inicializado
        inventoryService.saveRoom(room);
        System.out.println("✅ Room added successfully: " + room.getName());
    }


    private void addHint() {
        System.out.print("Enter hint description: ");
        String description = scanner.nextLine();

        System.out.print("Enter hint theme: ");
        String theme = scanner.nextLine();

        System.out.print("Enter related Room ID: ");
        int roomId = readInt();

        System.out.print("Enter hint price (€): ");
        double price = readDouble();

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
        int roomId = readInt();

        System.out.print("Enter decoration price (€): ");
        double price = readDouble();

        Decoration decoration = new Decoration(name, material, price, roomId);
        inventoryService.saveDecoration(decoration);
        System.out.println("✅ Decoration added successfully.");
    }

    // ------------------- READ -------------------

    private void showInventory() {
        System.out.println("\n=== 🧱 ROOMS ===");
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

    // ------------------- DELETE -------------------

    private void deleteRoom() {
        System.out.print("Enter the Room ID to delete: ");
        int id = readInt();

        Room room = inventoryService.findRoomById(id);
        if (room != null) {
            inventoryService.removeRoom(room);
            System.out.println("🗑️ Room deleted successfully.");
        } else {
            System.out.println("❌ Room not found.");
        }
    }

    private void deleteHint() {
        System.out.print("Enter the Hint ID to delete: ");
        int id = readInt();

        Hint hint = inventoryService.findHintById(id);
        if (hint != null) {
            inventoryService.removeHint(hint);
            System.out.println("🗑️ Hint deleted successfully.");
        } else {
            System.out.println("❌ Hint not found.");
        }
    }

    private void deleteDecoration() {
        System.out.print("Enter the Decoration ID to delete: ");
        int id = readInt();

        Decoration decoration = inventoryService.findDecorationById(id);
        if (decoration != null) {
            inventoryService.removeDecoration(decoration);
            System.out.println("🗑️ Decoration deleted successfully.");
        } else {
            System.out.println("❌ Decoration not found.");
        }
    }

    // ------------------- INPUT VALIDATION -------------------

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid number, please enter an integer: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid number, please enter a decimal: ");
            }
        }
    }
}
