package service;

import dao.EscapeRoomDaoImpl;
import exception.DataNotFoundException;
import exception.InvalidDataException;
import exception.OperationFailedException;
import model.EscapeRoom;

import java.util.List;
import java.util.Optional;

public class EscapeRoomService {

    private final EscapeRoomDaoImpl escapeRoomDao = new EscapeRoomDaoImpl();

    public String createEscapeRoom(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException("❌ Escape Room name cannot be empty.");
        }

        EscapeRoom escapeRoom = new EscapeRoom(name);
        boolean created = escapeRoomDao.save(escapeRoom);

        if (!created) {
            throw new OperationFailedException("❌ Error creating Escape Room. Please try again.");
        }

        return "✅ Escape Room created successfully: " + escapeRoom.getName();
    }

    public String listEscapeRooms() {
        List<EscapeRoom> rooms = escapeRoomDao.findAll();
        if (rooms.isEmpty()) {
            throw new DataNotFoundException("⚠️ No Escape Rooms found.");
        }

        StringBuilder sb = new StringBuilder("\n=== LIST OF ESCAPE ROOMS ===\n");
        rooms.forEach(r -> sb.append(r).append("\n"));
        return sb.toString();
    }

    public List<EscapeRoom> findAllEscapeRooms() {
        List<EscapeRoom> rooms = escapeRoomDao.findAll();
        if (rooms.isEmpty()) {
            throw new DataNotFoundException("⚠️ No Escape Rooms found.");
        }
        return rooms;
    }

    public Optional<EscapeRoom> findEscapeRoomById(int id) {
        return escapeRoomDao.findById(id);
    }

    public String deleteEscapeRoomById(int id) {
        EscapeRoom er = escapeRoomDao.findById(id)
                .orElseThrow(() -> new DataNotFoundException("❌ No Escape Room found with ID " + id));

        boolean deleted = escapeRoomDao.remove(er);
        if (!deleted) {
            throw new OperationFailedException("❌ Error deleting Escape Room. Try again.");
        }

        return "✅ Escape Room deleted successfully: " + er.getName();
    }
}
