package dao;

import config.DatabaseConfig;
import model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDaoImpl implements GenericDao<Ticket> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(Ticket ticket) {
        String sql = "INSERT INTO ticket (player_id, room_id, price, purchase_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ticket.getPlayerId());
            stmt.setInt(2, ticket.getRoomId());
            stmt.setDouble(3, ticket.getPrice());
            stmt.setTimestamp(4, Timestamp.valueOf(ticket.getPurchaseDate()));

            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) ticket.setId(rs.getInt(1));
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving Ticket: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, player_id, room_id, price, purchase_date FROM ticket";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("player_id"),
                        rs.getInt("room_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price")
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Tickets: " + e.getMessage());
        }

        return tickets;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        String sql = "SELECT id, player_id, room_id, price, purchase_date FROM ticket WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("player_id"),
                        rs.getInt("room_id"),
                        rs.getTimestamp("purchase_date").toLocalDateTime(),
                        rs.getDouble("price")
                );
                return Optional.of(ticket);
            }

        } catch (SQLException e) {
            System.err.println("Error finding Ticket by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    public List<Ticket> findByPlayerId(int playerId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, player_id, room_id, price, purchase_date FROM ticket WHERE player_id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket(
                            rs.getInt("id"),
                            rs.getInt("player_id"),
                            rs.getInt("room_id"),
                            rs.getTimestamp("purchase_date").toLocalDateTime(),
                            rs.getDouble("price")
                    );
                    tickets.add(ticket);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error finding tickets by player ID: " + e.getMessage());
        }

        return tickets;
    }

    @Override
    public boolean remove(Ticket ticket) {
        String sql = "DELETE FROM ticket WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting Ticket: " + e.getMessage());
            return false;
        }
    }
}
