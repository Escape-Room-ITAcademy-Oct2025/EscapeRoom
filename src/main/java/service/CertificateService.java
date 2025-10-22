package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import dao.TicketDaoImpl;
import exception.PlayerNotFoundException;
import exception.RoomNotFoundException;
import exception.TicketNotFoundException;
import model.Player;
import model.Room;
import model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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
        return ticketDao.findByPlayerId(playerId);
    }

    public String generateCertificateFromTicket(int ticketId) {
        Ticket ticket = ticketDao.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with ID " + ticketId + " not found."));

        Player player = playerDao.findById(ticket.getPlayerId())
                .orElseThrow(() -> new PlayerNotFoundException("Player not found for ticket ID " + ticketId));

        Room room = roomDao.findById(ticket.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found for ticket ID " + ticketId));

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(ticket.getPurchaseDate(), now);

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        String timeOfCompletion = String.format("%02dh %02dm", hours, minutes);

        return """
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
    }
}
