package dao;

import config.DatabaseConfig;
import model.Hint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HintDaoImpl implements GenericDao<Hint> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Hint hint) {
        String sql = "INSERT INTO clue (description, theme, room_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, hint.getDescription());
            stmt.setString(2, hint.getHintType().name());
            stmt.setInt(3, hint.getRoomId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    hint.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Hint> getAll() {
        List<Hint> hints = new ArrayList<>();
        String sql = "SELECT * FROM clue";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Hint hint = new Hint(
                        rs.getInt("id"),
                        rs.getString("description"),
                        Enum.valueOf(model.HintType.class, rs.getString("theme")),
                        rs.getInt("room_id"),
                        rs.getDouble("price")
                );
                hints.add(hint);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hints;
    }

    @Override
    public Hint getById(int id) {
        String sql = "SELECT * FROM clue WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Hint(
                            rs.getInt("id"),
                            rs.getString("description"),
                            Enum.valueOf(model.HintType.class, rs.getString("theme")),
                            rs.getInt("room_id"),
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
    public void remove(Hint hint) {
        String sql = "DELETE FROM clue WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hint.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
