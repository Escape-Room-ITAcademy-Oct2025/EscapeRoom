package dao;

import config.DatabaseConfig;
import model.Hint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Hint.
 * Gestiona las operaciones CRUD sobre la tabla 'hint' en la base de datos.
 */
public class HintDaoImpl implements GenericDao<Hint> {

    private Connection getConnection() throws SQLException {
        // Usa una única forma de obtener la conexión
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Hint hint) {
        String sql = "INSERT INTO hint (description, theme, room_id, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, hint.getDescription());
            stmt.setString(2, hint.getTheme());
            stmt.setInt(3, hint.getRoomId());
            stmt.setDouble(4, hint.getPrice());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    hint.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting hint: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Hint> findAll() {
        List<Hint> hints = new ArrayList<>();
        String sql = "SELECT * FROM hint";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Hint hint = new Hint(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("theme"),
                        rs.getInt("room_id"),
                        rs.getDouble("price")
                );
                hints.add(hint);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching hints: " + e.getMessage());
            e.printStackTrace();
        }

        return hints;
    }

    @Override
    public Hint findById(int id) {
        String sql = "SELECT * FROM hint WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Hint(
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getString("theme"),
                            rs.getInt("room_id"),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding hint by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Hint> findByRoomId(int roomId) {
        List<Hint> hints = new ArrayList<>();
        String sql = "SELECT id, description, theme, room_id, price FROM hint WHERE room_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Hint hint = new Hint(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("theme"),
                        rs.getInt("room_id"),
                        rs.getDouble("price")
                );
                hints.add(hint);
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving hints for room_id = " + roomId);
            e.printStackTrace();
        }

        return hints;
    }

    @Override
    public void remove(Hint hint) {
        String sql = "DELETE FROM hint WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hint.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting hint: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
