package dao;

import config.DatabaseConfig;
import model.Decoration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecorationDaoImpl implements GenericDao<Decoration> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(Decoration decoration) {
        String sql = "INSERT INTO decoration (name, material, price, room_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, decoration.getName());
            stmt.setString(2, decoration.getMaterial());
            stmt.setDouble(3, decoration.getPrice());
            stmt.setInt(4, decoration.getRoomId());
            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) decoration.setId(rs.getInt(1));
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving Decoration: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Decoration> findAll() {
        List<Decoration> decorations = new ArrayList<>();
        String sql = "SELECT id, name, material, price, room_id FROM decoration";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Decoration decoration = new Decoration(
                        rs.getString("name"),
                        rs.getString("material"),
                        rs.getDouble("price"),
                        rs.getInt("room_id")
                );
                decoration.setId(rs.getInt("id"));
                decorations.add(decoration);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Decorations: " + e.getMessage());
        }

        return decorations;
    }

    @Override
    public Optional<Decoration> findById(int id) {
        String sql = "SELECT id, name, material, price, room_id FROM decoration WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Decoration decoration = new Decoration(
                        rs.getString("name"),
                        rs.getString("material"),
                        rs.getDouble("price"),
                        rs.getInt("room_id")
                );
                decoration.setId(rs.getInt("id"));
                return Optional.of(decoration);
            }

        } catch (SQLException e) {
            System.err.println("Error finding Decoration by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean remove(Decoration decoration) {
        String sql = "DELETE FROM decoration WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, decoration.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting Decoration: " + e.getMessage());
            return false;
        }
    }
}
