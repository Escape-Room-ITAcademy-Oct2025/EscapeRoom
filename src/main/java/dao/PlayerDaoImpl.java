package dao;

import config.DatabaseConfig;
import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements GenericDao<Player> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(Player player) {
        String sql = "INSERT INTO player (name, email) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, player.getName());
            stmt.setString(2, player.getEmail());
            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    player.setId(rs.getInt(1));
                }
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting Player: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT id, name, email FROM player";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Player player = new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                players.add(player);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Players: " + e.getMessage());
        }

        return players;
    }

    @Override
    public Optional<Player> findById(int id) {
        String sql = "SELECT id, name, email FROM player WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Player player = new Player(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                    return Optional.of(player);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding Player by ID: " + e.getMessage());
        }

        return Optional.empty();
    }


    @Override
    public boolean remove(Player player) {
        String sql = "DELETE FROM player WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, player.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting Player: " + e.getMessage());
            return false;
        }
    }
}
