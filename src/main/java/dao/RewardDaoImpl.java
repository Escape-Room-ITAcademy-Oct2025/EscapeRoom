package dao;

import config.DatabaseConfig;
import model.Reward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RewardDaoImpl implements GenericDao<Reward> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(Reward reward) {
        String sql = "INSERT INTO reward (player_id, name, description) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reward.getPlayerId());
            stmt.setString(2, reward.getName());
            stmt.setString(3, reward.getDescription());
            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) reward.setId(rs.getInt(1));
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving reward: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Reward> findAll() {
        List<Reward> rewards = new ArrayList<>();
        String sql = "SELECT id, player_id, name, description, date_awarded FROM reward";

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
        }

        return rewards;
    }

    @Override
    public Optional<Reward> findById(int id) {
        String sql = "SELECT id, player_id, name, description, date_awarded FROM reward WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Reward reward = new Reward(
                        rs.getInt("id"),
                        rs.getInt("player_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("date_awarded").toLocalDateTime()
                );
                return Optional.of(reward);
            }

        } catch (SQLException e) {
            System.err.println("Error finding reward: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean remove(Reward reward) {
        String sql = "DELETE FROM reward WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reward.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting reward: " + e.getMessage());
            return false;
        }
    }
}
