package service;

import dao.TicketDaoImpl;
import model.Ticket;

import java.util.List;

/**
 * Gestiona la venta de tickets y el cálculo de ingresos totales.
 */
public class SalesService {

    private final TicketDaoImpl ticketDao;

    public SalesService() {
        this.ticketDao = new TicketDaoImpl();
    }

    public List<Ticket> findAllTickets() {
        return ticketDao.findAll();
    }

    public Ticket findTicketById(int id) {
        return ticketDao.findById(id);
    }

    public void addTicket(Ticket ticket) {
        ticketDao.save(ticket);
    }

    public double calculateTotalRevenue() {
        return ticketDao.findAll().stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }
}
