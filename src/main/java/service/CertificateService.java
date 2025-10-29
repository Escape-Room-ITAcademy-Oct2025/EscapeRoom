package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import dao.TicketDaoImpl;
import dao.RewardDaoImpl;
import exception.PlayerNotFoundException;
import exception.TicketNotFoundException;
import model.*;
import utils.InputUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CertificateService {

    private final PlayerDaoImpl playerDao;
    private final RoomDaoImpl roomDao;
    private final TicketDaoImpl ticketDao;
    private final RewardDaoImpl rewardDao;

    public CertificateService() {
        this.playerDao = new PlayerDaoImpl();
        this.roomDao = new RoomDaoImpl();
        this.ticketDao = new TicketDaoImpl();
        this.rewardDao = new RewardDaoImpl();
    }

    public void startCertificateFlow(Scanner scanner) {
        List<Player> players = playerDao.findAll();
        if (players.isEmpty()) {
            System.out.println("⚠️ No players found.");
            return;
        }

        System.out.println("\n=== Available Players ===");
        players.forEach(p ->
                System.out.printf("  [%d] %s (%s)%n", p.getId(), p.getName(), p.getEmail())
        );

        System.out.print("Enter player ID: ");
        int playerId = InputUtils.readInt(scanner);
        Optional<Player> optPlayer = playerDao.findById(playerId);
        if (optPlayer.isEmpty()) {
            throw new PlayerNotFoundException("❌ Player not found.");
        }

        List<Ticket> tickets = ticketDao.findByPlayerId(playerId);
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("⚠️ This player has no tickets.");
        }

        System.out.println("\n=== Tickets for " + optPlayer.get().getName() + " ===");
        tickets.forEach(t -> System.out.printf(
                "  [%d] Room ID: %d | Price: %.2f € | Purchased: %s%n",
                t.getId(), t.getRoomId(), t.getPrice(), t.getPurchaseDate()
        ));

        System.out.print("Enter ticket ID: ");
        int ticketId = InputUtils.readInt(scanner);

        generateCertificateFromTicket(ticketId, scanner)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("❌ Could not generate certificate.")
                );
    }

    public Optional<String> generateCertificateFromTicket(int ticketId, Scanner scanner) {
        Optional<Ticket> optTicket = ticketDao.findById(ticketId);
        if (optTicket.isEmpty()) {
            throw new TicketNotFoundException("❌ Ticket not found.");
        }

        Ticket ticket = optTicket.get();
        Optional<Player> optPlayer = playerDao.findById(ticket.getPlayerId());
        if (optPlayer.isEmpty()) {
            throw new PlayerNotFoundException("❌ Player not found for this ticket.");
        }

        Optional<Room> optRoom = roomDao.findById(ticket.getRoomId());
        if (optRoom.isEmpty()) {
            throw new IllegalStateException("❌ Room not found for this ticket.");
        }

        Player player = optPlayer.get();
        Room room = optRoom.get();

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(ticket.getPurchaseDate(), now);
        String timeOfCompletion = String.format("%02dh %02dm", duration.toHours(), duration.toMinutesPart());

        RewardType rewardType = askForReward(scanner);

        Reward reward = new Reward(player.getId(), rewardType.getName(), rewardType.getDescription());
        rewardDao.save(reward);

        String certificate = """
            =====================================================
                    📜 ESCAPE ROOM COMPLETION CERTIFICATE
            =====================================================
            Player: %s
            Room:   %s
            Difficulty:  %s
            -----------------------------------------------------
            Started:   %s
            Completed: %s
            Time of completion: %s
            -----------------------------------------------------
            🏅 Reward: %s
            📜 Description: %s
            -----------------------------------------------------
            Congratulations, %s! You have successfully completed
            the escape room "%s". 🎉
            =====================================================
            """.formatted(
                player.getName(),
                room.getName(),
                room.getDifficulty(),
                ticket.getPurchaseDate(),
                now,
                timeOfCompletion,
                rewardType.getName(),
                rewardType.getDescription(),
                player.getName(),
                room.getName()
        );

        return Optional.of(certificate);
    }

    private RewardType askForReward(Scanner scanner) {
        System.out.println("\n=== Choose a medal to include ===");
        RewardType[] values = RewardType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf("%d. %s%n", i + 1, values[i]);
        }

        int choice = -1;
        while (choice < 1 || choice > values.length) {
            System.out.print("Select a medal number: ");
            choice = InputUtils.readInt(scanner);

            if (choice < 1 || choice > values.length) {
                System.out.println("⚠️ Invalid choice. Please enter a number between 1 and " + values.length + ".");
            }
        }

        RewardType selected = values[choice - 1];
        System.out.println("🏅 " + selected.getName() + " has been selected.\n");
        return selected;
    }

}
