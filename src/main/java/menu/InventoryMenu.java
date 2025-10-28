package menu;

import model.Decoration;
import model.EscapeRoom;
import model.Hint;
import model.Room;
import service.EscapeRoomService;
import service.InventoryService;
import utils.InputUtils;
import exception.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InventoryMenu {

    private final InventoryService inventoryService;
    private final EscapeRoomService escapeRoomService;
    private final Scanner scanner;

    public InventoryMenu(Scanner scanner) {
        this.inventoryService = InventoryService.getInstance();
        this.escapeRoomService = EscapeRoomService.getInstance();
        this.scanner = scanner;
    }

    public void start() {
        int option;
        do {
            try {
                printMenu();
                option = InputUtils.readInt(scanner);

                switch (option) {
                    case 1 -> addRoom();
                    case 2 -> addHint();
                    case 3 -> addDecoration();
                    case 4 -> showFullInventory();
                    case 5 -> showTotalValue();
                    case 6 -> deleteRoom();
                    case 7 -> deleteHint();
                    case 8 -> deleteDecoration();
                    case 0 -> System.out.println("Returning to Admin Menu...");
                    default -> System.out.println("❌ Invalid option. Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                scanner.nextLine();
                option = -1;
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
        List<EscapeRoom> escapeRooms;

        try {
            escapeRooms = escapeRoomService.findAllEscapeRooms();
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\n=== Available Escape Rooms ===");
        escapeRooms.forEach(er ->
                System.out.printf("ID: %d | Name: %s%n", er.getId(), er.getName())
        );

        try {
            System.out.print("\nEnter Escape Room ID to assign this room to: ");
            int escapeRoomId = InputUtils.readInt(scanner);

            boolean exists = escapeRooms.stream().anyMatch(er -> er.getId() == escapeRoomId);
            if (!exists) {
                System.out.println("❌ Invalid ID. Operation cancelled.");
                return;
            }

            System.out.print("Enter room name: ");
            String name = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter difficulty (EASY, MEDIUM, HARD): ");
            String difficulty = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter price (€): ");
            double price = InputUtils.readDouble(scanner);

            System.out.println(inventoryService.addRoom(name, difficulty, price, escapeRoomId));

        } catch (EscapeRoomNotFoundException | InvalidInputException | DatabaseOperationException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid numeric value. Try again.");
            scanner.nextLine();
        }
    }

    private void addHint() {
        List<Room> rooms;
        try {
            rooms = inventoryService.getAllRooms();
        } catch (RoomNotFoundException e) {
            System.out.println("⚠️ No rooms available. Please create one first.");
            return;
        }

        System.out.println("\n=== Available Rooms ===");
        rooms.forEach(r ->
                System.out.printf("ID: %d | Name: %s | Difficulty: %s%n",
                        r.getId(), r.getName(), r.getDifficulty())
        );

        try {
            System.out.print("\nEnter related Room ID: ");
            int roomId = InputUtils.readInt(scanner);

            boolean roomExists = rooms.stream().anyMatch(r -> r.getId() == roomId);
            if (!roomExists) {
                System.out.println("❌ Invalid Room ID. Operation cancelled.");
                return;
            }

            System.out.print("Enter hint description: ");
            String description = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter hint theme: ");
            String theme = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter hint price (€): ");
            double price = InputUtils.readDouble(scanner);

            System.out.println(inventoryService.addHint(description, theme, roomId, price));

        } catch (InvalidInputException | DatabaseOperationException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid numeric value.");
            scanner.nextLine();
        }
    }


    private void addDecoration() {
        List<model.Room> rooms;
        try {
            rooms = inventoryService.getAllRooms();
        } catch (RoomNotFoundException e) {
            System.out.println("⚠️ No rooms available. Please create one first.");
            return;
        }

        System.out.println("\n=== Available Rooms ===");
        rooms.forEach(r ->
                System.out.printf("ID: %d | Name: %s | Difficulty: %s%n",
                        r.getId(), r.getName(), r.getDifficulty())
        );

        try {
            System.out.print("\nEnter related Room ID: ");
            int roomId = InputUtils.readInt(scanner);

            boolean roomExists = rooms.stream().anyMatch(r -> r.getId() == roomId);
            if (!roomExists) {
                System.out.println("❌ Invalid Room ID. Operation cancelled.");
                return;
            }

            System.out.print("Enter decoration name: ");
            String name = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter material: ");
            String material = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter decoration price (€): ");
            double price = InputUtils.readDouble(scanner);

            System.out.println(inventoryService.addDecoration(name, material, roomId, price));

        } catch (InvalidInputException | DatabaseOperationException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid numeric value.");
            scanner.nextLine();
        }
    }

    private void showFullInventory() {
        try {
            System.out.println(inventoryService.showFullInventory());
        } catch (RoomNotFoundException | HintNotFoundException | DecorationNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showTotalValue() {
        try {
            System.out.println(inventoryService.showTotalValue());
        } catch (DatabaseOperationException e) {
            System.out.println("⚠️ Could not calculate total value: " + e.getMessage());
        }
    }

    private void deleteRoom() {
        List<Room> rooms;
        try {
            rooms = inventoryService.getAllRooms();
        } catch (RoomNotFoundException e) {
            System.out.println("⚠️ No rooms available to delete.");
            return;
        }

        System.out.println("\n=== Available Rooms ===");
        rooms.forEach(r ->
                System.out.printf("ID: %d | Name: %s | Difficulty: %s%n",
                        r.getId(), r.getName(), r.getDifficulty())
        );

        try {
            System.out.print("Enter Room ID to delete: ");
            int id = InputUtils.readInt(scanner);

            Optional<Room> roomOpt = rooms.stream().filter(r -> r.getId() == id).findFirst();
            if (roomOpt.isEmpty()) {
                System.out.println("❌ Invalid Room ID. Operation cancelled.");
                return;
            }

            Room room = roomOpt.get();
            System.out.printf("⚠️ Are you sure you want to delete '%s'? (yes/no): ", room.getName());
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("yes")) {
                System.out.println("❎ Deletion cancelled.");
                return;
            }

            System.out.println(inventoryService.deleteRoomById(id));

        } catch (RoomNotFoundException | DatabaseOperationException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid ID format.");
            scanner.nextLine();
        }
    }

    private void deleteHint() {
        List<Hint> hints;
        try {
            hints = inventoryService.getAllHints();
        } catch (HintNotFoundException e) {
            System.out.println("⚠️ No hints available to delete.");
            return;
        }

        System.out.println("\n=== Available Hints ===");
        hints.forEach(h ->
                System.out.printf("ID: %d | Description: %s | Room ID: %d | Price: %.2f €%n",
                        h.getId(), h.getDescription(), h.getRoomId(), h.getPrice())
        );

        try {
            System.out.print("\nEnter Hint ID to delete: ");
            int id = InputUtils.readInt(scanner);

            Optional<Hint> hintOpt = hints.stream().filter(h -> h.getId() == id).findFirst();
            if (hintOpt.isEmpty()) {
                System.out.println("❌ Invalid Hint ID. Operation cancelled.");
                return;
            }

            Hint hint = hintOpt.get();
            System.out.printf("⚠️ Are you sure you want to delete this hint? \"%s\" (yes/no): ",
                    hint.getDescription());
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("yes")) {
                System.out.println("❎ Deletion cancelled.");
                return;
            }

            System.out.println(inventoryService.deleteHintById(id));

        } catch (HintNotFoundException | DatabaseOperationException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid numeric value.");
            scanner.nextLine();
        }
    }


    private void deleteDecoration() {
        List<Decoration> decorations;
        try {
            decorations = inventoryService.getAllDecorations();
        } catch (DecorationNotFoundException e) {
            System.out.println("⚠️ No decorations available to delete.");
            return;
        }

        System.out.println("\n=== Available Decorations ===");
        decorations.forEach(d ->
                System.out.printf("ID: %d | Name: %s | Material: %s | Room ID: %d | Price: %.2f €%n",
                        d.getId(), d.getName(), d.getMaterial(), d.getRoomId(), d.getPrice())
        );

        try {
            System.out.print("\nEnter Decoration ID to delete: ");
            int id = InputUtils.readInt(scanner);

            Optional<Decoration> decorationOpt = decorations.stream()
                    .filter(d -> d.getId() == id)
                    .findFirst();

            if (decorationOpt.isEmpty()) {
                System.out.println("❌ Invalid Decoration ID. Operation cancelled.");
                return;
            }

            Decoration decoration = decorationOpt.get();
            System.out.printf("⚠️ Are you sure you want to delete '%s' ? (yes/no): ",
                    decoration.getName());
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (!(confirm.equals("yes") || confirm.equals("y"))) {
                System.out.println("❎ Deletion cancelled.");
                return;
            }

            System.out.println(inventoryService.deleteDecorationById(id));

        } catch (DecorationNotFoundException | DatabaseOperationException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("⚠️ Invalid numeric value.");
            scanner.nextLine();
        }
    }

}
