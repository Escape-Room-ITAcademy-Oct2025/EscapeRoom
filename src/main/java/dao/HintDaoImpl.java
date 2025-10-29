package dao;

import config.DatabaseConfig;
import model.Hint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HintDaoImpl implements GenericDao<Hint> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(Hint hint) {
        String sql = "INSERT INTO hint (description, theme, room_id, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, hint.getDescription());
            stmt.setString(2, hint.getTheme());
            stmt.setInt(3, hint.getRoomId());
            stmt.setDouble(4, hint.getPrice());
            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) hint.setId(rs.getInt(1));
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving Hint: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Hint> findAll() {
        List<Hint> hints = new ArrayList<>();
        String sql = "SELECT id, description, theme, room_id, price FROM hint";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Hint hint = new Hint(
                        rs.getString("description"),
                        rs.getString("theme"),
                        rs.getInt("room_id"),
                        rs.getDouble("price")
                );
                hint.setId(rs.getInt("id"));
                hints.add(hint);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Hints: " + e.getMessage());
        }

        return hints;
    }

    @Override
    public Optional<Hint> findById(int id) {
        String sql = "SELECT id, description, theme, room_id, price FROM hint WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Hint hint = new Hint(
                        rs.getString("description"),
                        rs.getString("theme"),
                        rs.getInt("room_id"),
                        rs.getDouble("price")
                );
                hint.setId(rs.getInt("id"));
                return Optional.of(hint);
            }

        } catch (SQLException e) {
            System.err.println("Error finding Hint by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean remove(Hint hint) {
        String sql = "DELETE FROM hint WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hint.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting Hint: " + e.getMessage());
            return false;
        }
    }
}
