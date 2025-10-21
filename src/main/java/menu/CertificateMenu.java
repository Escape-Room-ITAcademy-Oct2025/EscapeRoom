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

    public void showMenu() {
        int option;
        do {
            System.out.println("\n--- Certificate Menu ---");
            System.out.println("1. Generate certificate");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> generateCertificate();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option, try again.");
            }

        } while (option != 0);
    }

    private void generateCertificate() {
        try {
            System.out.print("Enter Player ID: ");
            int playerId = InputUtils.readInt(scanner);

            System.out.print("Enter Room ID: ");
            int roomId = InputUtils.readInt(scanner);

            String certificate = certificateService.generateCertificate(playerId, roomId);
            System.out.println("\n" + certificate);

        } catch (Exception e) {
            System.out.println("Error generating certificate: " + e.getMessage());
        }
    }
}
