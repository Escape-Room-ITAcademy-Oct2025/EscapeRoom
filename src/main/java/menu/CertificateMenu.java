package menu;

import exception.PlayerNotFoundException;
import exception.TicketNotFoundException;
import model.Player;
import model.Ticket;
import service.CertificateService;
import utils.InputUtils;

import java.util.List;
import java.util.Optional;
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

            try {
                switch (option) {
                    case 1 -> generateCertificateFlow();
                    case 0 -> System.out.println("Returning to Admin Menu...");
                    default -> System.out.println("❌ Invalid option, please try again.");
                }
            } catch (PlayerNotFoundException | TicketNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("⚠️ Internal error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Unexpected error occurred: " + e.getMessage());
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

        System.out.println("\nPick one of the available players:");
        players.forEach(p -> System.out.printf("  [%d] %s (%s)%n", p.getId(), p.getName(), p.getEmail()));

        int playerId = InputUtils.readInt(scanner);

        List<Ticket> tickets = certificateService.getTicketsByPlayerId(playerId);
        // certificateService ya lanza TicketNotFoundException si no hay tickets

        System.out.println("\nTickets for this player:");
        tickets.forEach(t -> System.out.printf(
                "  [%d] Room ID: %d | Price: %.2f € | Purchased: %s%n",
                t.getId(), t.getRoomId(), t.getPrice(), t.getPurchaseDate()
        ));

        System.out.print("Enter ticket ID to generate certificate: ");
        int ticketId = InputUtils.readInt(scanner);

        Optional<String> certificate = certificateService.generateCertificateFromTicket(ticketId);
        certificate.ifPresent(System.out::println);
    }
}
