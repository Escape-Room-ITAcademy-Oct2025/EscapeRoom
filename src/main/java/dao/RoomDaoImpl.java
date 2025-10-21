package dao;

import config.DatabaseConfig;
import model.Room;

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
        String sql = "INSERT INTO room (name, difficulty, price, escape_room_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, room.getName());
            stmt.setString(2, room.getDifficulty().name());
            stmt.setDouble(3, room.getPrice());
            stmt.setInt(4, room.getEscapeRoomId());
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
                Room room = new Room(
                        rs.getString("name"),
                        Enum.valueOf(model.Difficulty.class, rs.getString("difficulty")),
                        rs.getDouble("price")
                );
                room.setId(rs.getInt("id"));
                room.setEscapeRoomId(rs.getInt("escape_room_id"));
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
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Room room = new Room(
                        rs.getString("name"),
                        Enum.valueOf(model.Difficulty.class, rs.getString("difficulty")),
                        rs.getDouble("price")
                );
                room.setId(rs.getInt("id"));
                room.setEscapeRoomId(rs.getInt("escape_room_id"));
                return Optional.of(room);
            }

        } catch (SQLException e) {
            System.err.println("Error finding Room by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    public List<Room> findByEscapeRoomId(int escapeRoomId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE escape_room_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escapeRoomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room(
                        rs.getString("name"),
                        Enum.valueOf(model.Difficulty.class, rs.getString("difficulty").toUpperCase()),
                        rs.getDouble("price")
                );
                room.setId(rs.getInt("id"));
                room.setEscapeRoomId(escapeRoomId);
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Rooms by EscapeRoom ID: " + e.getMessage());
        }

        return rooms;
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
