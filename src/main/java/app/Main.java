package app;

import menu.AdminMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  VIRTUAL ESCAPE ROOM MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        new AdminMenu().start();
    }
}
