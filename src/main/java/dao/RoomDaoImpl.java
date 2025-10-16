package dao;

import config.DatabaseConfig;
import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Room.
 * Gestiona las operaciones CRUD sobre la tabla 'room' en la base de datos.
 */
public class RoomDaoImpl implements GenericDao<Room> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Room room) {
        String sql = "INSERT INTO room (name, difficulty, price) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, room.getName());
            stmt.setString(2, room.getDifficulty());
            stmt.setDouble(3, room.getPrice());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    room.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting room: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("difficulty"),
                        rs.getDouble("price")
                );
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public Room findById(int id) {
        String sql = "SELECT * FROM room WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("difficulty"),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding room by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Devuelve todas las salas filtradas por dificultad.
     *
     * @param difficulty nivel de dificultad (Easy, Medium, Hard)
     * @return lista de salas que coinciden con esa dificultad
     */
    public List<Room> findByDifficulty(String difficulty) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, name, difficulty, price FROM room WHERE difficulty = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, difficulty);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("difficulty"),
                        rs.getDouble("price")
                );
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving rooms by difficulty '" + difficulty + "': " + e.getMessage());
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public void remove(Room room) {
        String sql = "DELETE FROM room WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, room.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
