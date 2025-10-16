package dao;

import config.DatabaseConfig;
import model.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements GenericDao<Ticket> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO ticket (player_id, room_id, purchase_date, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ticket.getPlayerId());
            stmt.setInt(2, ticket.getRoomId());
            stmt.setDate(3, Date.valueOf(ticket.getPurchaseDate()));
            stmt.setDouble(4, ticket.getPrice());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM ticket";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("player_id"),
                        rs.getInt("room_id"),
                        rs.getDate("purchase_date").toLocalDate(),
                        rs.getDouble("price")
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public Ticket getById(int id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                            rs.getInt("id"),
                            rs.getInt("player_id"),
                            rs.getInt("room_id"),
                            rs.getDate("purchase_date").toLocalDate(),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(Ticket ticket) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
