package dao;

import config.DatabaseConfig;
import model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoImpl implements GenericDao<Player> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Player player) {
        String sql = "INSERT INTO player (name, email) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, player.getName());
            stmt.setString(2, player.getEmail());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    player.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting player: " + e.getMessage());
        }
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM player";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                players.add(new Player(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching players: " + e.getMessage());
        }

        return players;
    }

    @Override
    public Player findById(int id) {
        String sql = "SELECT * FROM player WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Player(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding player: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void remove(Player player) {
        String sql = "DELETE FROM player WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, player.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting player: " + e.getMessage());
        }
    }
}
