package service;

import dao.DecorationDaoImpl;
import dao.HintDaoImpl;
import dao.RoomDaoImpl;
import exception.*;
import model.Decoration;
import model.Difficulty;
import model.EscapeRoom;
import model.Hint;
import model.Room;

import java.util.List;
import java.util.Optional;

public class InventoryService {

    private final RoomDaoImpl roomDao;
    private final HintDaoImpl hintDao;
    private final DecorationDaoImpl decorationDao;
    private final EscapeRoomService escapeRoomService;

    public InventoryService() {
        this.roomDao = new RoomDaoImpl();
        this.hintDao = new HintDaoImpl();
        this.decorationDao = new DecorationDaoImpl();
        this.escapeRoomService = new EscapeRoomService();
    }

    public String addRoom(String name, String difficultyInput, double price, int escapeRoomId) {
        Optional<EscapeRoom> er = escapeRoomService.findEscapeRoomById(escapeRoomId);
        if (er.isEmpty()) {
            throw new EscapeRoomNotFoundException("❌ Escape Room ID not found. Operation cancelled.");
        }

        if (name == null || name.isBlank()) {
            throw new InvalidInputException("❌ Room name cannot be empty.");
        }

        Difficulty difficulty;
        try {
            difficulty = Difficulty.valueOf(difficultyInput.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("⚠️ Invalid difficulty. Valid values: EASY, MEDIUM, HARD.");
        }

        Room room = new Room(name, difficulty, price);
        room.setEscapeRoomId(escapeRoomId);

        if (!roomDao.save(room)) {
            throw new RuntimeException("❌ Error saving room. Please try again.");
        }

        return "✅ Room added successfully: " + room.getName() + " (EscapeRoom ID: " + escapeRoomId + ")";
    }

    public String listAllRooms() {
        List<Room> rooms = roomDao.findAll();
        if (rooms.isEmpty()) throw new RoomNotFoundException("⚠️ No rooms found.");
        StringBuilder sb = new StringBuilder("\n=== 🧩 ROOMS ===\n");
        rooms.forEach(r -> sb.append(r).append("\n"));
        return sb.toString();
    }

    public String deleteRoomById(int id) {
        Room room = roomDao.findById(id).orElseThrow(() -> new RoomNotFoundException("❌ Room not found."));
        if (!roomDao.remove(room)) {
            throw new RuntimeException("❌ Error deleting room.");
        }
        return "🗑️ Room deleted successfully.";
    }

    public String addHint(String description, String theme, int roomId, double price) {
        if (description == null || description.isBlank()) {
            throw new InvalidInputException("❌ Hint description cannot be empty.");
        }

        Hint hint = new Hint(description, theme, roomId, price);
        if (!hintDao.save(hint)) {
            throw new RuntimeException("❌ Error saving hint.");
        }
        return "✅ Hint added successfully.";
    }

    public String listAllHints() {
        List<Hint> hints = hintDao.findAll();
        if (hints.isEmpty()) throw new HintNotFoundException("⚠️ No hints found.");
        StringBuilder sb = new StringBuilder("\n=== 💡 HINTS ===\n");
        hints.forEach(h -> sb.append(h).append("\n"));
        return sb.toString();
    }

    public String deleteHintById(int id) {
        Hint hint = hintDao.findById(id).orElseThrow(() -> new HintNotFoundException("❌ Hint not found."));
        if (!hintDao.remove(hint)) {
            throw new RuntimeException("❌ Error deleting hint.");
        }
        return "🗑️ Hint deleted successfully.";
    }

    public String addDecoration(String name, String material, int roomId, double price) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("❌ Decoration name cannot be empty.");
        }

        Decoration decoration = new Decoration(name, material, price, roomId);
        if (!decorationDao.save(decoration)) {
            throw new RuntimeException("❌ Error saving decoration.");
        }
        return "✅ Decoration added successfully.";
    }

    public String listAllDecorations() {
        List<Decoration> decorations = decorationDao.findAll();
        if (decorations.isEmpty()) throw new DecorationNotFoundException("⚠️ No decorations found.");
        StringBuilder sb = new StringBuilder("\n=== 🎨 DECORATIONS ===\n");
        decorations.forEach(d -> sb.append(d).append("\n"));
        return sb.toString();
    }

    public String deleteDecorationById(int id) {
        Decoration decoration = decorationDao.findById(id)
                .orElseThrow(() -> new DecorationNotFoundException("❌ Decoration not found."));
        if (!decorationDao.remove(decoration)) {
            throw new RuntimeException("❌ Error deleting decoration.");
        }
        return "🗑️ Decoration deleted successfully.";
    }

    public String showFullInventory() {
        return listAllRooms() + listAllHints() + listAllDecorations();
    }

    public String showTotalValue() {
        double totalRooms = roomDao.findAll().stream().mapToDouble(Room::getPrice).sum();
        double totalHints = hintDao.findAll().stream().mapToDouble(Hint::getPrice).sum();
        double totalDecorations = decorationDao.findAll().stream().mapToDouble(Decoration::getPrice).sum();

        double total = totalRooms + totalHints + totalDecorations;
        return String.format("💰 Total Inventory Value: %.2f €", total);
    }
}
