package menu;

import service.SalesService;
import model.Ticket;
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

            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> salesService.findAllTickets()
                        .forEach(System.out::println);
                case 2 -> findTicketById();
                case 3 -> addNewTicket();
                case 4 -> {
                    double totalRevenue = salesService.calculateTotalRevenue();
                    System.out.printf("Total Revenue: %.2f€%n", totalRevenue);
                }
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option, try again.");
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void findTicketById() {
        System.out.print("Enter ticket ID: ");
        int id = InputUtils.readInt(scanner);
        Ticket ticket = salesService.findTicketById(id);
        System.out.println(ticket != null ? ticket : "Ticket not found");
    }

    private void addNewTicket() {
        try {
            System.out.print("Player ID: ");
            int playerId = InputUtils.readInt(scanner);

            System.out.print("Room ID: ");
            int roomId = InputUtils.readInt(scanner);

            System.out.print("Purchase date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(InputUtils.readNonEmptyString(scanner));
            LocalDateTime dateTime = date.atStartOfDay();

            System.out.print("Price: ");
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
