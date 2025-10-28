package menu;

import service.CertificateService;
import utils.InputUtils;
import exception.PlayerNotFoundException;
import exception.TicketNotFoundException;

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
                    case 1 -> certificateService.startCertificateFlow(scanner);
                    case 0 -> System.out.println("Returning to Admin Menu...");
                    default -> System.out.println("❌ Invalid option, please try again.");
                }
            } catch (PlayerNotFoundException | TicketNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Unexpected error: " + e.getMessage());
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
}
