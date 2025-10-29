package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import dao.TicketDaoImpl;
import exception.InvalidDataException;
import exception.DataNotFoundException;
import exception.OperationFailedException;
import model.Player;
import model.Room;
import model.Ticket;
import model.observer.Observer;
import service.InventoryService;
import service.EscapeRoomService;

import java.util.List;
import java.util.Optional;

public class SalesService {

    private final PlayerDaoImpl playerDao;
    private final RoomDaoImpl roomDao;
    private final TicketDaoImpl ticketDao;
    private final InventoryService inventoryService;
    private final EscapeRoomService escapeRoomService;

    public SalesService() {
        this.playerDao = new PlayerDaoImpl();
        this.roomDao = new RoomDaoImpl();
        this.ticketDao = new TicketDaoImpl();
        this.inventoryService = InventoryService.getInstance();
        this.escapeRoomService = EscapeRoomService.getInstance();
    }

    public String sellTicket(String playerName, String playerEmail, int roomId, double price) {
        if (playerName == null || playerName.isBlank()) {
            throw new InvalidDataException("❌ Player name cannot be empty.");
        }
        if (playerEmail == null || playerEmail.isBlank()) {
            throw new InvalidDataException("❌ Player email cannot be empty.");
        }
        if (price <= 0) {
            throw new InvalidDataException("❌ Invalid ticket price. Must be greater than 0.");
        }

        Optional<Player> existingPlayer = playerDao.findByEmail(playerEmail);
        Player player;

        if (existingPlayer.isPresent()) {
            player = existingPlayer.get();
        } else {
            Player newPlayer = new Player(playerName, playerEmail,false);
            boolean created = playerDao.save(newPlayer);
            if (!created) {
                throw new OperationFailedException("❌ Could not create player.");
            }

            player = playerDao.findByEmail(playerEmail)
                    .orElseThrow(() -> new DataNotFoundException("⚠️ Player created but not found afterward."));
        }

        Room room = roomDao.findById(roomId)
                .orElseThrow(() -> new DataNotFoundException("❌ Room not found."));

        Ticket ticket = new Ticket(player.getId(), room.getId(), price);
        boolean saved = ticketDao.save(ticket);
        if (!saved) {
            throw new OperationFailedException("❌ Could not create ticket.");
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
        List<Ticket> tickets = ticketDao.findAll();
        if (tickets == null || tickets.isEmpty()) {
            throw new DataNotFoundException("⚠️ No tickets found.");
        }
        return tickets;
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
        return ticketOpt.map(ticketDao::remove)
                .orElseThrow(() -> new DataNotFoundException("❌ Ticket not found with ID: " + id));
    }
    public String registerNewPlayer(String name, String email, boolean subscribe) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException("❌ Player name cannot be empty.");
        }
        if (email == null || email.isBlank()) {
            throw new InvalidDataException("❌ Player email cannot be empty.");
        }

        if (playerDao.findByEmail(email).isPresent()) {
            throw new OperationFailedException("⚠️ A player with this email already exists.");
        }

        Player newPlayer = new Player(name, email,subscribe);
        boolean created = playerDao.save(newPlayer);

        if (!created) {
            throw new OperationFailedException("❌ Error registering new player.");
        }

        if (subscribe) {
            playerDao.updateSubscriptionStatus(email, true);
            escapeRoomService.registerObserver(newPlayer);
            inventoryService.registerObserver(newPlayer);
            return "✅ Player registered and subscribed to Escape Room updates.";
        }

        return "✅ Player registered successfully (not subscribed).";
    }

    public String subscribeExistingPlayer(String email) {
        Optional<Player> playerOpt = playerDao.findByEmail(email);
        if (playerOpt.isEmpty()) {
            throw new DataNotFoundException("❌ No player found with email: " + email);
        }

        Player player = playerOpt.get();

        if (player.isSubscribed()) {
            return "⚠️ Player " + player.getName() + " is already subscribed to updates.";
        }

        boolean updated = playerDao.updateSubscriptionStatus(email, true);
        if (!updated) {
            throw new OperationFailedException("❌ Could not update subscription status for player.");
        }

        player.setSubscribed(true);
        escapeRoomService.registerObserver(player);
        inventoryService.registerObserver(player);

        return "✅ Player " + player.getName() + " subscribed to Escape Room updates.";
    }

    public String unsubscribePlayer(String email) {
        Optional<Player> playerOpt = playerDao.findByEmail(email);
        if (playerOpt.isEmpty()) {
            throw new DataNotFoundException("❌ No player found with email: " + email);
        }

        Player player = playerOpt.get();

        if (!player.isSubscribed()) {
            return "⚠️ Player " + player.getName() + " is already unsubscribed.";
        }

        boolean updated = playerDao.updateSubscriptionStatus(email, false);
        if (!updated) {
            throw new OperationFailedException("❌ Could not update subscription status for player.");
        }

        player.setSubscribed(false);
        escapeRoomService.removeObserver(player);
        inventoryService.removeObserver(player);

        return "🛑 Player " + player.getName() + " unsubscribed from updates.";
    }

    public void restorePlayerSubscriptions() {
        List<Player> allPlayers = playerDao.findAll();
        allPlayers.stream()
                .filter(Player::isSubscribed)
                .forEach(player -> {
                    escapeRoomService.registerObserver(player);
                    inventoryService.registerObserver(player);
                });
    }
}
