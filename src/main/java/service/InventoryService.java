package service;

import dao.DecorationDaoImpl;
import dao.HintDaoImpl;
import dao.RoomDaoImpl;
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
            return "❌ Escape Room ID not found. Operation cancelled.";
        }

        if (name == null || name.isBlank()) {
            return "❌ Room name cannot be empty.";
        }

        Difficulty difficulty;
        try {
            difficulty = Difficulty.valueOf(difficultyInput.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            difficulty = Difficulty.EASY;
            return "⚠️ Invalid difficulty! Defaulting to EASY.";
        }

        Room room = new Room(name, difficulty, price);
        room.setEscapeRoomId(escapeRoomId);

        boolean saved = roomDao.save(room);
        if (saved)
            return "✅ Room added successfully: " + room.getName() + " (EscapeRoom ID: " + escapeRoomId + ")";
        else
            return "❌ Error saving room. Please try again.";
    }

    public String listAllRooms() {
        List<Room> rooms = roomDao.findAll();
        if (rooms.isEmpty()) return "⚠️ No rooms found.";
        StringBuilder sb = new StringBuilder("\n=== 🧩 ROOMS ===\n");
        rooms.forEach(r -> sb.append(r).append("\n"));
        return sb.toString();
    }

    public String deleteRoomById(int id) {
        Optional<Room> roomOpt = roomDao.findById(id);
        if (roomOpt.isEmpty()) return "❌ Room not found.";
        boolean deleted = roomDao.remove(roomOpt.get());
        return deleted ? "🗑️ Room deleted successfully." : "❌ Error deleting room.";
    }

    public String addHint(String description, String theme, int roomId, double price) {
        if (description == null || description.isBlank()) {
            return "❌ Hint description cannot be empty.";
        }

        Hint hint = new Hint(description, theme, roomId, price);
        boolean saved = hintDao.save(hint);
        return saved ? "✅ Hint added successfully." : "❌ Error saving hint.";
    }

    public String listAllHints() {
        List<Hint> hints = hintDao.findAll();
        if (hints.isEmpty()) return "⚠️ No hints found.";
        StringBuilder sb = new StringBuilder("\n=== 💡 HINTS ===\n");
        hints.forEach(h -> sb.append(h).append("\n"));
        return sb.toString();
    }

    public String deleteHintById(int id) {
        Optional<Hint> hintOpt = hintDao.findById(id);
        if (hintOpt.isEmpty()) return "❌ Hint not found.";
        boolean deleted = hintDao.remove(hintOpt.get());
        return deleted ? "🗑️ Hint deleted successfully." : "❌ Error deleting hint.";
    }

    public String addDecoration(String name, String material, int roomId, double price) {
        if (name == null || name.isBlank()) {
            return "❌ Decoration name cannot be empty.";
        }

        Decoration decoration = new Decoration(name, material, price, roomId);
        boolean saved = decorationDao.save(decoration);
        return saved ? "✅ Decoration added successfully." : "❌ Error saving decoration.";
    }

    public String listAllDecorations() {
        List<Decoration> decorations = decorationDao.findAll();
        if (decorations.isEmpty()) return "⚠️ No decorations found.";
        StringBuilder sb = new StringBuilder("\n=== 🎨 DECORATIONS ===\n");
        decorations.forEach(d -> sb.append(d).append("\n"));
        return sb.toString();
    }

    public String deleteDecorationById(int id) {
        Optional<Decoration> decOpt = decorationDao.findById(id);
        if (decOpt.isEmpty()) return "❌ Decoration not found.";
        boolean deleted = decorationDao.remove(decOpt.get());
        return deleted ? "🗑️ Decoration deleted successfully." : "❌ Error deleting decoration.";
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
