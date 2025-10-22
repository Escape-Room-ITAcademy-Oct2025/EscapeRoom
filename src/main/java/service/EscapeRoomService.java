package service;

import dao.EscapeRoomDaoImpl;
import model.EscapeRoom;

import java.util.List;
import java.util.Optional;

public class EscapeRoomService {

    private final EscapeRoomDaoImpl escapeRoomDao = new EscapeRoomDaoImpl();

    public String createEscapeRoom(String name) {
        if (name == null || name.isBlank()) {
            return "❌ Escape Room name cannot be empty.";
        }

        EscapeRoom escapeRoom = new EscapeRoom(name);
        boolean created = escapeRoomDao.save(escapeRoom);

        if (created) {
            return "✅ Escape Room created successfully: " + escapeRoom.getName();
        } else {
            return "❌ Error creating Escape Room. Please try again.";
        }
    }

    public String listEscapeRooms() {
        List<EscapeRoom> rooms = escapeRoomDao.findAll();
        if (rooms.isEmpty()) {
            return "⚠️ No Escape Rooms found.";
        }

        StringBuilder sb = new StringBuilder("\n=== LIST OF ESCAPE ROOMS ===\n");
        rooms.forEach(r -> sb.append(r).append("\n"));
        return sb.toString();
    }

    public List<EscapeRoom> findAllEscapeRooms() {
        return escapeRoomDao.findAll();
    }

    public Optional<EscapeRoom> findEscapeRoomById(int id) {
        return escapeRoomDao.findById(id);
    }

    public String deleteEscapeRoomById(int id) {
        Optional<EscapeRoom> maybeRoom = escapeRoomDao.findById(id);
        if (maybeRoom.isEmpty()) {
            return "❌ No Escape Room found with ID " + id;
        }

        EscapeRoom er = maybeRoom.get();
        boolean deleted = escapeRoomDao.delete(er);

        if (deleted) {
            return "✅ Escape Room deleted successfully: " + er.getName();
        } else {
            return "❌ Error deleting Escape Room. Try again.";
        }
    }
}
