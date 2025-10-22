package menu;

import model.Player;
import model.Ticket;
import service.CertificateService;
import utils.InputUtils;

import java.util.List;
import java.util.Scanner;

public class CertificateMenu {

    private final CertificateService certificateService;
    private final Scanner scanner;

    public CertificateMenu(Scanner scanner) {
        this.certificateService = new CertificateService();
        this.scanner = scanner;
    }

    public void start() {
        int option;
        do {
            printMenuOptions();
            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> generateCertificateFlow();
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("❌ Invalid option, please try again.");
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);
    }

    private void printMenuOptions() {
        System.out.println("\n=== 🏅 CERTIFICATE MENU ===");
        System.out.println("1. Generate certificate");
        System.out.println("0. Return to Admin Menu");
        System.out.print("Select an option: ");
    }

    private void generateCertificateFlow() {
        List<Player> players = certificateService.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("⚠️ No players found.");
            return;
        }

        System.out.println("\nAvailable players:");
        players.forEach(p -> System.out.printf("  [%d] %s (%s)%n", p.getId(), p.getName(), p.getEmail()));

        int playerId = InputUtils.readInt(scanner);
        List<Ticket> tickets = certificateService.getTicketsByPlayerId(playerId);

        if (tickets.isEmpty()) {
            System.out.println("⚠️ This player has no tickets.");
            return;
        }

        System.out.println("\nTickets for this player:");
        tickets.forEach(t -> System.out.printf(
                "  [%d] Room ID: %d | Price: %.2f € | Purchased: %s%n",
                t.getId(), t.getRoomId(), t.getPrice(), t.getPurchaseDate()
        ));

        System.out.print("Enter ticket ID to generate certificate: ");
        int ticketId = InputUtils.readInt(scanner);

        certificateService.generateCertificateFromTicket(ticketId)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("❌ Could not generate certificate.")
                );
    }
}
