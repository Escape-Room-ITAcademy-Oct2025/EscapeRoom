package dao;

import config.DatabaseConfig;
import model.Room;
import model.Difficulty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements GenericDao<Room> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(Room room) {
        String sql = "INSERT INTO room (name, difficulty, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, room.getName());
            stmt.setString(2, room.getDifficulty().name());
            stmt.setDouble(3, room.getPrice());
            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) room.setId(rs.getInt(1));
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving Room: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, name, difficulty, price FROM room";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Difficulty difficulty = Difficulty.valueOf(rs.getString("difficulty"));
                Room room = new Room(
                        rs.getString("name"),
                        difficulty,
                        rs.getDouble("price")
                );
                room.setId(rs.getInt("id"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Rooms: " + e.getMessage());
        }

        return rooms;
    }

    @Override
    public Optional<Room> findById(int id) {
        String sql = "SELECT id, name, difficulty, price FROM room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Difficulty difficulty = Difficulty.valueOf(rs.getString("difficulty"));
                    return new Room(
                            rs.getInt("id"),
                            rs.getString("name"),
                            difficulty,
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
     * @param difficulty nivel de dificultad (EASY, MEDIUM, HARD)
     * @return lista de salas que coinciden con esa dificultad
     */
    public List<Room> findByDifficulty(Difficulty difficulty) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT id, name, difficulty, price FROM room WHERE difficulty = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, difficulty.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Difficulty diff = Difficulty.valueOf(rs.getString("difficulty"));
                Room room = new Room(
                        rs.getString("name"),
                        diff,
                        rs.getDouble("price")
                );
                room.setId(rs.getInt("id"));
                return Optional.of(room);
            }

        } catch (SQLException e) {
            System.err.println("Error finding Room by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean remove(Room room) {
        String sql = "DELETE FROM room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, room.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting Room: " + e.getMessage());
            return false;
        }
    }
}
