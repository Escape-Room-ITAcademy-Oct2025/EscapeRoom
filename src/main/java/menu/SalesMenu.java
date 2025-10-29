package menu;

import model.Room;
import service.InventoryService;
import service.SalesService;
import utils.InputUtils;
import exception.InvalidDataException;
import exception.DataNotFoundException;
import exception.OperationFailedException;

import java.util.List;
import java.util.Scanner;

public class SalesMenu {

    private final SalesService salesService;
    private final InventoryService inventoryService;
    private final Scanner scanner;

    public SalesMenu(Scanner scanner) {
        this.salesService = new SalesService();
        this.inventoryService = InventoryService.getInstance();
        this.scanner = scanner;
    }

    public void start() {
        int option;

        do {
            printMenuOptions();
            option = InputUtils.readInt(scanner);

            try {
                switch (option) {
                    case 1 -> sellTicketFlow();
                    case 2 -> showTotalRevenue();
                    case 3 -> listTickets();
                    case 4 -> registerNewPlayerFlow();
                    case 5 -> subscribeExistingPlayerFlow();
                    case 6 -> unsubscribePlayerFlow();
                    case 7 -> listPlayers();
                    case 0 -> System.out.println("Returning to Admin Menu...");
                    default -> System.out.println("❌ Invalid option, try again.");
                }
            } catch (InvalidDataException | DataNotFoundException | OperationFailedException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Unexpected error: " + e.getMessage());
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void printMenuOptions() {
        System.out.println("\n=== 🎟️ SALES MENU ===");
        System.out.println("1. Sell ticket");
        System.out.println("2. Show total revenue (€)");
        System.out.println("3. Show all tickets");
        System.out.println("4. Register new player");
        System.out.println("5. Subscribe existing player to updates");
        System.out.println("6. Unsubscribe player from updates");
        System.out.println("7. Show all players");
        System.out.println("0. Return to Admin Menu");
        System.out.print("Select an option: ");
    }

    private void sellTicketFlow() {
        List<Room> rooms;
        try {
            rooms = inventoryService.getAllRooms();
        } catch (DataNotFoundException e) {
            System.out.println("⚠️ No rooms available for ticket sale. Please create one first.");
            return;
        }

        System.out.println("\n=== Available Rooms ===");
        rooms.forEach(r ->
                System.out.printf("ID: %d | Name: %s | Difficulty: %s | Price: %.2f €%n",
                        r.getId(), r.getName(), r.getDifficulty(), r.getPrice())
        );

        try {
            System.out.print("\nEnter room ID: ");
            int roomId = InputUtils.readInt(scanner);

            System.out.print("Enter player name: ");
            String name = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter player email: ");
            String email = InputUtils.readNonEmptyString(scanner);

            boolean roomExists = rooms.stream().anyMatch(r -> r.getId() == roomId);
            if (!roomExists) {
                System.out.println("❌ Invalid Room ID. Operation cancelled.");
                return;
            }

            System.out.print("Enter ticket price (€): ");
            double price = InputUtils.readDouble(scanner);

            String result = salesService.sellTicket(name, email, roomId, price);
            System.out.println("\n" + result);

        } catch (InvalidDataException | DataNotFoundException | OperationFailedException e) {
            System.out.println(e.getMessage());
        }
    }


    private void showTotalRevenue() {
        try {
            double total = salesService.calculateTotalRevenue();
            System.out.printf("💰 Total revenue: %.2f €%n", total);
        } catch (Exception e) {
            System.out.println("⚠️ Error getting revenue: " + e.getMessage());
        }
    }

    private void listTickets() {
        try {
            System.out.println("\n=== ALL TICKETS ===");
            salesService.findAllTickets().forEach(System.out::println);
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerNewPlayerFlow() {
        try {
            System.out.print("Enter player name: ");
            String name = InputUtils.readNonEmptyString(scanner);

            System.out.print("Enter player email: ");
            String email = InputUtils.readNonEmptyString(scanner);

            System.out.print("Subscribe to updates? (yes/no): ");
            String subscribeChoice = scanner.nextLine().trim();
            boolean subscribe = subscribeChoice.equalsIgnoreCase("yes");

            String result = salesService.registerNewPlayer(name, email, subscribe);
            System.out.println("\n" + result);
        } catch (InvalidDataException | OperationFailedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void subscribeExistingPlayerFlow() {
        try {
            System.out.print("Enter player email: ");
            String email = InputUtils.readNonEmptyString(scanner);

            String result = salesService.subscribeExistingPlayer(email);
            System.out.println("\n" + result);
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void unsubscribePlayerFlow() {
        try {
            System.out.print("Enter player email: ");
            String email = InputUtils.readNonEmptyString(scanner);

            String result = salesService.unsubscribePlayer(email);
            System.out.println("\n" + result);
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listPlayers() {
        try {
            System.out.println("\n=== 👥 REGISTERED PLAYERS ===");
            salesService.findAllPlayers().forEach(p ->
                    System.out.printf("ID: %d | Name: %s | Email: %s | Subscribed: %s%n",
                            p.getId(),
                            p.getName(),
                            p.getEmail(),
                            p.isSubscribed() ? "✅" : "❌")
            );
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error listing players: " + e.getMessage());
        }
    }

}
