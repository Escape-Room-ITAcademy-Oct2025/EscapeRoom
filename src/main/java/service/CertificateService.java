package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import dao.TicketDaoImpl;
import exception.PlayerNotFoundException;
import exception.TicketNotFoundException;
import model.Player;
import model.Room;
import model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CertificateService {

    private final PlayerDaoImpl playerDao;
    private final RoomDaoImpl roomDao;
    private final TicketDaoImpl ticketDao;

    public CertificateService() {
        this.playerDao = new PlayerDaoImpl();
        this.roomDao = new RoomDaoImpl();
        this.ticketDao = new TicketDaoImpl();
    }

    public List<Player> getAllPlayers() {
        return playerDao.findAll();
    }

    public List<Ticket> getTicketsByPlayerId(int playerId) {
        List<Ticket> tickets = ticketDao.findByPlayerId(playerId);
        if (tickets == null || tickets.isEmpty()) {
            throw new TicketNotFoundException("⚠️ This player has no tickets.");
        }
        return tickets;
    }

    public Optional<String> generateCertificateFromTicket(int ticketId) {
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

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        String timeOfCompletion = String.format("%02dh %02dm", hours, minutes);

        String certificate = """
            =====================================================
                    📜 ESCAPE ROOM COMPLETION CERTIFICATE
            =====================================================
            Player: %s
            Room:   %s
            Difficulty:  %s
            -----------------------------------------------------
            Ticket ID: #%d
            Started:   %s
            Completed: %s
            Time of completion: %s
            -----------------------------------------------------
            Congratulations, %s! You have successfully completed
            the escape room "%s". 🎉
            =====================================================
            """.formatted(
                player.getName(),
                room.getName(),
                room.getDifficulty(),
                ticket.getId(),
                ticket.getPurchaseDate(),
                now,
                timeOfCompletion,
                player.getName(),
                room.getName()
        );

        return Optional.of(certificate);
    }
}
