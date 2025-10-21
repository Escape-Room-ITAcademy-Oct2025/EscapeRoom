package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import dao.TicketDaoImpl;
import model.Player;
import model.Room;
import model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class SalesService {

    private final PlayerDaoImpl playerDao;
    private final RoomDaoImpl roomDao;
    private final TicketDaoImpl ticketDao;

    public SalesService() {
        this.playerDao = new PlayerDaoImpl();
        this.roomDao = new RoomDaoImpl();
        this.ticketDao = new TicketDaoImpl();
    }

    public String sellTicket(String playerName, String playerEmail, int roomId, double price) {
        if (playerName == null || playerName.isBlank()) {
            return "❌ Player name cannot be empty.";
        }
        if (playerEmail == null || playerEmail.isBlank()) {
            return "❌ Player email cannot be empty.";
        }
        if (price <= 0) {
            return "❌ Invalid ticket price. Must be greater than 0.";
        }

        Optional<Player> existingPlayer = playerDao.findByEmail(playerEmail);
        Player player;

        if (existingPlayer.isPresent()) {
            player = existingPlayer.get();
        } else {
            Player newPlayer = new Player(playerName, playerEmail);
            boolean created = playerDao.save(newPlayer);
            if (!created) return "❌ Could not create player.";
            player = playerDao.findByEmail(playerEmail).orElse(null);
            if (player == null) return "⚠️ Player created but not found afterward.";
        }

        Optional<Room> optRoom = roomDao.findById(roomId);
        if (optRoom.isEmpty()) {
            return "❌ Room not found.";
        }
        Room room = optRoom.get();

        Ticket ticket = new Ticket(player.getId(), room.getId(), price);
        boolean saved = ticketDao.save(ticket);

        if (!saved) {
            return "❌ Could not create ticket.";
        }

        return String.format(
                "✅ Ticket created successfully!\nPlayer: %s (%s)\nRoom: %s (%.2f €)\nDate: %s",
                player.getName(),
                player.getEmail(),
                room.getName(),
                price,
                ticket.getPurchaseDate().toString()
        );
    }

    public List<Ticket> findAllTickets() {
        return ticketDao.findAll();
    }

    public Optional<Ticket> findTicketById(int id) {
        return ticketDao.findById(id);
    }

    public double calculateTotalRevenue() {
        return ticketDao.findAll()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    public boolean deleteTicketById(int id) {
        Optional<Ticket> ticketOpt = ticketDao.findById(id);
        return ticketOpt.map(ticketDao::remove).orElse(false);
    }
}
