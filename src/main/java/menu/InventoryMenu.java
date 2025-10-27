package menu;

import service.InventoryService;
import utils.InputUtils;

import java.util.Scanner;

public class InventoryMenu {

    private final InventoryService inventoryService;
    private final Scanner scanner;

    public InventoryMenu(Scanner scanner) {
        this.inventoryService = InventoryService.getInstance();
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
                case 4 -> System.out.println(inventoryService.showFullInventory());
                case 5 -> System.out.println(inventoryService.showTotalValue());
                case 6 -> deleteRoom();
                case 7 -> deleteHint();
                case 8 -> deleteDecoration();
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
        System.out.print("Enter Escape Room ID: ");
        int escapeRoomId = InputUtils.readInt(scanner);
        String name = InputUtils.readNonEmptyString(scanner, "Enter room name: ");
        System.out.print("Enter difficulty (EASY, MEDIUM, HARD): ");
        String difficulty = scanner.nextLine();
        System.out.print("Enter price (€): ");
        double price = InputUtils.readDouble(scanner);
        System.out.println(inventoryService.addRoom(name, difficulty, price, escapeRoomId));
    }

    private void addHint() {
        String description = InputUtils.readNonEmptyString(scanner, "Enter hint description: ");
        System.out.print("Enter hint theme: ");
        String theme = scanner.nextLine();
        System.out.print("Enter related Room ID: ");
        int roomId = InputUtils.readInt(scanner);
        System.out.print("Enter hint price (€): ");
        double price = InputUtils.readDouble(scanner);
        System.out.println(inventoryService.addHint(description, theme, roomId, price));
    }

    private void addDecoration() {
        String name = InputUtils.readNonEmptyString(scanner, "Enter decoration name: ");
        System.out.print("Enter material: ");
        String material = scanner.nextLine();
        System.out.print("Enter related Room ID: ");
        int roomId = InputUtils.readInt(scanner);
        System.out.print("Enter decoration price (€): ");
        double price = InputUtils.readDouble(scanner);
        System.out.println(inventoryService.addDecoration(name, material, roomId, price));
    }

    private void deleteRoom() {
        System.out.print("Enter Room ID to delete: ");
        int id = InputUtils.readInt(scanner);
        System.out.println(inventoryService.deleteRoomById(id));
    }

    private void deleteHint() {
        System.out.print("Enter Hint ID to delete: ");
        int id = InputUtils.readInt(scanner);
        System.out.println(inventoryService.deleteHintById(id));
    }

    private void deleteDecoration() {
        System.out.print("Enter Decoration ID to delete: ");
        int id = InputUtils.readInt(scanner);
        System.out.println(inventoryService.deleteDecorationById(id));
    }
}
