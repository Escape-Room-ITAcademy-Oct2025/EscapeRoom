package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import dao.TicketDaoImpl;
import exception.InvalidPlayerDataException;
import exception.RoomNotFoundException;
import exception.TicketCreationException;
import model.Player;
import model.Room;
import model.Ticket;

import java.util.List;

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
            throw new InvalidPlayerDataException("Player name cannot be empty.");
        }
        if (playerEmail == null || playerEmail.isBlank()) {
            throw new InvalidPlayerDataException("Player email cannot be empty.");
        }
        if (price <= 0) {
            throw new TicketCreationException("Invalid ticket price. Must be greater than 0.");
        }

        Player player = playerDao.findByEmail(playerEmail)
                .orElseGet(() -> {
                    Player newPlayer = new Player(playerName, playerEmail);
                    boolean created = playerDao.save(newPlayer);
                    if (!created) {
                        throw new TicketCreationException("Could not create player.");
                    }
                    return playerDao.findByEmail(playerEmail)
                            .orElseThrow(() -> new TicketCreationException("Player created but not found afterward."));
                });

        Room room = roomDao.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " was not found."));

        Ticket ticket = new Ticket(player.getId(), room.getId(), price);
        boolean saved = ticketDao.save(ticket);
        if (!saved) {
            throw new TicketCreationException("Could not create ticket.");
        }

        return String.format(
                "✅ Ticket created successfully!\nPlayer: %s (%s)\nRoom: %s (%.2f €)\nDate: %s",
                player.getName(),
                player.getEmail(),
                room.getName(),
                price,
                ticket.getPurchaseDate()
        );
    }

    public List<Ticket> findAllTickets() {
        return ticketDao.findAll();
    }

    public Ticket findTicketById(int id) {
        return ticketDao.findById(id)
                .orElseThrow(() -> new TicketCreationException("Ticket with ID " + id + " was not found."));
    }

    public double calculateTotalRevenue() {
        return ticketDao.findAll()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    public boolean deleteTicketById(int id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new TicketCreationException("Ticket with ID " + id + " was not found."));
        return ticketDao.remove(ticket);
    }
}
