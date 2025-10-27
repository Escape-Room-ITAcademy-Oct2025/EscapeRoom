package menu;

import service.SalesService;
import utils.InputUtils;
import exception.InvalidDataException;
import exception.DataNotFoundException;
import exception.OperationFailedException;

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

            try {
                switch (option) {
                    case 1 -> sellTicketFlow();
                    case 2 -> showTotalRevenue();
                    case 3 -> listTickets();
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
        System.out.println("0. Return to Admin Menu");
        System.out.print("Select an option: ");
    }

    private void sellTicketFlow() {
        String name = InputUtils.readNonEmptyString(scanner, "Enter player name: ");
        String email = InputUtils.readNonEmptyString(scanner, "Enter player email: ");
        System.out.print("Enter room ID: ");
        int roomId = InputUtils.readInt(scanner);
        System.out.print("Enter ticket price (€): ");
        double price = InputUtils.readDouble(scanner);

        String result = salesService.sellTicket(name, email, roomId, price);
        System.out.println("\n" + result);
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
