package dao;

import config.DatabaseConfig;
import model.Reward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Reward.
 */
public class RewardDaoImpl implements GenericDao<Reward> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Reward reward) {
        String sql = "INSERT INTO reward (player_id, name, description) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reward.getPlayerId());
            stmt.setString(2, reward.getName());
            stmt.setString(3, reward.getDescription());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    reward.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting reward: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Reward> findAll() {
        List<Reward> rewards = new ArrayList<>();
        String sql = "SELECT * FROM reward";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reward reward = new Reward(
                        rs.getInt("id"),
                        rs.getInt("player_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("date_awarded").toLocalDateTime()
                );
                rewards.add(reward);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching rewards: " + e.getMessage());
            e.printStackTrace();
        }

        return rewards;
    }

    @Override
    public Reward findById(int id) {
        String sql = "SELECT * FROM reward WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Reward(
                            rs.getInt("id"),
                            rs.getInt("player_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("date_awarded").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding reward: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(Reward reward) {
        String sql = "DELETE FROM reward WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reward.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting reward: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
