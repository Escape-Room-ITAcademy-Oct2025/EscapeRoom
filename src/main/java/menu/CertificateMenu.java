package menu;

import service.CertificateService;
import utils.InputUtils;

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
            System.out.println("\n--- Certificate Menu ---");
            System.out.println("1. Generate certificate");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> generateCertificate();
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

    private void generateCertificate() {
        try {
            System.out.print("Enter Player ID: ");
            int playerId = InputUtils.readInt(scanner);

            System.out.print("Enter Room ID: ");
            int roomId = InputUtils.readInt(scanner);

        String certificate = certificateService.generateCertificate(playerId, roomId);

        System.out.println();
        System.out.println(certificate);
    }
}
