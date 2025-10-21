package menu;

import service.SalesService;
import model.Ticket;
import utils.InputUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class SalesMenu {

    private final SalesService salesService;
    private final Scanner scanner;

    public SalesMenu(Scanner scanner) {
        this.salesService = new SalesService();
        this.scanner = scanner;
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n--- Sales Menu ---");
            System.out.println("1. Show all tickets");
            System.out.println("2. Find ticket by ID");
            System.out.println("3. Add new ticket");
            System.out.println("4. Calculate total revenue");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

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

            Ticket ticket = new Ticket(0, playerId, roomId, dateTime, price);
            salesService.addTicket(ticket);

            System.out.println("Ticket added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding ticket: " + e.getMessage());
        }
    }
}
