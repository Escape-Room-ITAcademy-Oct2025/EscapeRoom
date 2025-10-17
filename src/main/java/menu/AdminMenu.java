package menu;

import java.util.Scanner;

public class AdminMenu {

    private final InventoryMenu inventoryMenu;
    private final SalesMenu salesMenu;
    private final CertificateMenu certificateMenu;
    private final Scanner scanner;

    public AdminMenu() {
        this.inventoryMenu = new InventoryMenu();
        this.salesMenu = new SalesMenu();
        this.certificateMenu = new CertificateMenu();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Inventory Management");
            System.out.println("2. Sales Management");
            System.out.println("3. Certificates");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> inventoryMenu.showMenu();
                case 2 -> salesMenu.showMenu();
                case 3 -> certificateMenu.showMenu();
                case 0 -> System.out.println("Exiting Admin Menu...");
                default -> System.out.println("Invalid option, try again.");
            }

        } while (option != 0);
    }
}
