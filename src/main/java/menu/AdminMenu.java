package menu;

import utils.InputUtils;

import java.util.Scanner;

public class AdminMenu {

    private final Scanner scanner;
    private final EscapeRoomMenu escapeRoomMenu;
    private final InventoryMenu inventoryMenu;
    private final SalesMenu salesMenu;
    private final CertificateMenu certificateMenu;

    public AdminMenu() {
        this.scanner = new Scanner(System.in);
        this.escapeRoomMenu = new EscapeRoomMenu(scanner);
        this.inventoryMenu = new InventoryMenu(scanner);
        this.salesMenu = new SalesMenu(scanner);
        this.certificateMenu = new CertificateMenu(scanner);
    }

    public void start() {
        int option;

        do {
            printMenuOptions();
            option = InputUtils.readInt(scanner);

            switch (option) {
                case 1 -> escapeRoomMenu.start();
                case 2 -> inventoryMenu.start();
                case 3 -> salesMenu.start();
                case 4 -> certificateMenu.start();
                case 0 -> System.out.println("👋 Exiting program... Goodbye!");
                default -> System.out.println("❌ Invalid option, try again.");
            }

            if (option != 0) InputUtils.pause(scanner);

        } while (option != 0);

        scanner.close();
    }

    private void printMenuOptions() {
        System.out.println("\n==============================");
        System.out.println("⚙️  ADMIN MENU");
        System.out.println("==============================");
        System.out.println("1. Manage Escape Rooms");
        System.out.println("2. Inventory Management");
        System.out.println("3. Sales Management");
        System.out.println("4. Certificates");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }
}
