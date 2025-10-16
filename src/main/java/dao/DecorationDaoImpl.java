package dao;

import config.DatabaseConfig;
import model.Decoration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DecorationDaoImpl implements GenericDao<Decoration> {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public void save(Decoration decoration) {
        String sql = "INSERT INTO decorations (type, cost) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, decoration.getType());
            stmt.setDouble(2, decoration.getCost());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    decoration.setId(rs.getInt(1)); // asigna el ID generado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Decoration> getAll() {
        List<Decoration> decorations = new ArrayList<>();
        String sql = "SELECT * FROM decorations";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Decoration decoration = new Decoration(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("cost")
                );
                decorations.add(decoration);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return decorations;
    }

    @Override
    public Decoration getById(int id) {
        String sql = "SELECT * FROM decorations WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Decoration(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getDouble("cost")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(Decoration decoration) {
        String sql = "DELETE FROM decorations WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, decoration.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
