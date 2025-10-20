package dao;

import config.DatabaseConfig;
import model.EscapeRoom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscapeRoomDaoImpl implements GenericDao<EscapeRoom> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(EscapeRoom escapeRoom) {
        String sql = "INSERT INTO escape_room (name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, escapeRoom.getName());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) escapeRoom.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Error saving EscapeRoom: " + e.getMessage());
        }
    }

    @Override
    public List<EscapeRoom> findAll() {
        List<EscapeRoom> escapeRooms = new ArrayList<>();
        String sql = "SELECT * FROM escape_room";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EscapeRoom er = new EscapeRoom(rs.getString("name"));
                er.setId(rs.getInt("id"));
                escapeRooms.add(er);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching EscapeRooms: " + e.getMessage());
        }

        return escapeRooms;
    }

    @Override
    public EscapeRoom findById(int id) {
        String sql = "SELECT * FROM escape_room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EscapeRoom er = new EscapeRoom(rs.getString("name"));
                er.setId(rs.getInt("id"));
                return er;
            }

        } catch (SQLException e) {
            System.err.println("Error finding EscapeRoom by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void remove(EscapeRoom escapeRoom) {
        String sql = "DELETE FROM escape_room WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, escapeRoom.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting EscapeRoom: " + e.getMessage());
        }
    }
}
