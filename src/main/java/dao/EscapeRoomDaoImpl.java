package dao;

import config.DatabaseConfig;
import model.EscapeRoom;
import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EscapeRoomDaoImpl implements GenericDao<EscapeRoom> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean save(EscapeRoom escapeRoom) {
        String sql = "INSERT INTO escape_room (name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, escapeRoom.getName());
            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) escapeRoom.setId(rs.getInt(1));
            }

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving EscapeRoom: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<EscapeRoom> findAll() {
        List<EscapeRoom> escapeRooms = new ArrayList<>();
        RoomDaoImpl roomDao = new RoomDaoImpl();
        String sql = "SELECT id, name FROM escape_room";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EscapeRoom er = new EscapeRoom(rs.getString("name"));
                er.setId(rs.getInt("id"));
                List<Room> rooms = roomDao.findByEscapeRoomId(er.getId());
                rooms.forEach(er::addRoom);
                escapeRooms.add(er);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching EscapeRooms: " + e.getMessage());
        }

        return escapeRooms;
    }

    @Override
    public Optional<EscapeRoom> findById(int id) {
        String sql = "SELECT id, name FROM escape_room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EscapeRoom er = new EscapeRoom(rs.getString("name"));
                er.setId(rs.getInt("id"));
                return Optional.of(er);
            }

        } catch (SQLException e) {
            System.err.println("Error finding EscapeRoom by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public boolean remove(EscapeRoom escapeRoom) {
        String sql = "DELETE FROM escape_room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escapeRoom.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting EscapeRoom: " + e.getMessage());
            return false;
        }
    }
}
