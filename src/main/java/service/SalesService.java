package service;

import dao.TicketDaoImpl;
import model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Handles all business logic related to ticket sales and revenue tracking.
 */
public class SalesService {

    private final TicketDaoImpl ticketDao;

    public SalesService() {
        this.ticketDao = new TicketDaoImpl();
    }

    // ────────────────────────────────
    // 🎟️ CREATE
    // ────────────────────────────────
    public boolean sellTicket(int playerId, int roomId, double price) {
        if (price <= 0) {
            System.out.println("⚠️ Invalid ticket price. Must be greater than 0.");
            return false;
        }

        Ticket ticket = new Ticket();
        ticket.setPlayerId(playerId);
        ticket.setRoomId(roomId);
        ticket.setPrice(price);
        ticket.setPurchaseDate(LocalDateTime.now());

        boolean success = ticketDao.save(ticket);

        if (success)
            System.out.printf("✅ Ticket #%d created successfully for player %d (room %d)%n",
                    ticket.getId(), playerId, roomId);
        else
            System.out.println("❌ Failed to save ticket.");

        return success;
    }

    // ────────────────────────────────
    // 📊 READ
    // ────────────────────────────────
    public List<Ticket> findAllTickets() {
        return ticketDao.findAll();
    }

    public Optional<Ticket> findTicketById(int id) {
        return ticketDao.findById(id);
    }

    // ────────────────────────────────
    // 💰 AGGREGATION
    // ────────────────────────────────
    public double calculateTotalRevenue() {
        return ticketDao.findAll()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    // ────────────────────────────────
    // 🗑️ DELETE
    // ────────────────────────────────
    public boolean deleteTicketById(int id) {
        Optional<Ticket> ticketOpt = ticketDao.findById(id);
        return ticketOpt.map(ticketDao::remove).orElse(false);
    }
}
