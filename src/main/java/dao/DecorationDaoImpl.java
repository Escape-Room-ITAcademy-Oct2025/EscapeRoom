package dao;

import config.DatabaseConfig;
import model.Decoration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Decoration.
 * Gestiona las operaciones CRUD sobre la tabla 'decoration' en la base de datos.
 */
public class DecorationDaoImpl implements GenericDao<Decoration> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Decoration decoration) {
        String sql = "INSERT INTO decoration (name, material, price, room_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, decoration.getName());
            stmt.setString(2, decoration.getMaterial());
            stmt.setDouble(3, decoration.getPrice());
            stmt.setInt(4, decoration.getRoomId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    decoration.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting decoration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Decoration> findAll() {
        List<Decoration> decorations = new ArrayList<>();
        String sql = "SELECT * FROM decoration";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Decoration decoration = new Decoration(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("material"),
                        rs.getDouble("price"),
                        rs.getInt("room_id")
                );
                decorations.add(decoration);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching decorations: " + e.getMessage());
            e.printStackTrace();
        }

        return decorations;
    }

    @Override
    public Decoration findById(int id) {
        String sql = "SELECT * FROM decoration WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Decoration(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("material"),
                            rs.getDouble("price"),
                            rs.getInt("room_id")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding decoration by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Devuelve todas las decoraciones asociadas a una sala concreta.
     *
     * @param roomId identificador de la sala
     * @return lista de objetos Decoration correspondientes a esa sala
     */
    public List<Decoration> findByRoomId(int roomId) {
        List<Decoration> decorations = new ArrayList<>();
        String sql = "SELECT id, name, material, price, room_id FROM decoration WHERE room_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Decoration decoration = new Decoration(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("material"),
                        rs.getDouble("price"),
                        rs.getInt("room_id")
                );
                decorations.add(decoration);
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving decorations for room_id = " + roomId);
            e.printStackTrace();
        }

        return decorations;
    }

    @Override
    public void remove(Decoration decoration) {
        String sql = "DELETE FROM decoration WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, decoration.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting decoration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
