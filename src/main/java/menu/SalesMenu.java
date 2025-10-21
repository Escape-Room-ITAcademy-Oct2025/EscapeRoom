package menu;

import service.SalesService;
import utils.InputUtils;

import java.util.Scanner;

public class SalesMenu {

    private final SalesService salesService;
    private final Scanner scanner;

    public SalesMenu(Scanner scanner) {
        this.salesService = new SalesService();
        this.scanner = scanner;
    }

    public void start() {
        int option;

        do {
            printMenuOptions();
            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> sellTicket();
                case 2 -> showTotalRevenue();
                case 3 -> listTickets();
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("❌ Invalid option, try again.");
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void printMenuOptions() {
        System.out.println("\n=== 🎟️ SALES MENU ===");
        System.out.println("1. Sell ticket");
        System.out.println("2. Show total revenue (€)");
        System.out.println("3. Show all tickets");
        System.out.println("0. Return to Admin Menu");
        System.out.print("Select an option: ");
    }

    private void sellTicket() {
        System.out.print("Enter Player ID: ");
        int playerId = InputUtils.readInt(scanner);

        System.out.print("Enter Room ID: ");
        int roomId = InputUtils.readInt(scanner);

        System.out.print("Enter ticket price (€): ");
        double price = InputUtils.readDouble(scanner);

        boolean success = salesService.sellTicket(playerId, roomId, price);

        if (success)
            System.out.println("✅ Ticket sold successfully!");
        else
            System.out.println("❌ Error selling ticket.");
    }

    private void showTotalRevenue() {
        double total = salesService.calculateTotalRevenue();
        System.out.printf("💰 Total revenue: %.2f €%n", total);
    }

    private void listTickets() {
        System.out.println("\n=== ALL TICKETS ===");
        salesService.findAllTickets().forEach(System.out::println);
    }
}
