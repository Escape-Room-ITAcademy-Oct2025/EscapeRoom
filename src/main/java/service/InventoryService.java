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
import model.observer.Observer;
import model.observer.Subject;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class InventoryService implements Subject {

    private static InventoryService instance;

    public static InventoryService getInstance() {
        if (instance == null) {
            instance = new InventoryService();
        }
        return instance;
    }

    private InventoryService() {
        this.roomDao = new RoomDaoImpl();
        this.hintDao = new HintDaoImpl();
        this.decorationDao = new DecorationDaoImpl();
        this.escapeRoomService = EscapeRoomService.getInstance();
    }

    private final RoomDaoImpl roomDao;
    private final HintDaoImpl hintDao;
    private final DecorationDaoImpl decorationDao;
    private final EscapeRoomService escapeRoomService;
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventMessage) {
        for (Observer observer : observers) {
            observer.update(eventMessage);
        }
    }

    public String addRoom(String name, String difficultyInput, double price, int escapeRoomId) {
        EscapeRoom escapeRoom = escapeRoomService.findEscapeRoomById(escapeRoomId)
                .orElseThrow(() -> new EscapeRoomNotFoundException("❌ Escape Room ID not found."));

        validateRoomName(name);
        Difficulty difficulty = parseDifficulty(difficultyInput);

        Room room = new Room(name, difficulty, price);
        room.setEscapeRoomId(escapeRoomId);

        if (!roomDao.save(room)) {
            throw new DatabaseOperationException("❌ Error saving room. Please try again.");
        }

        notifyObservers("[NEW] Room created: " + room.getName() + " (" + difficulty + ") in " + escapeRoom.getName());
        return "✅ Room " + room.getName() + " created successfully.";
    }

    public String listAllRooms() {
        List<Room> rooms = roomDao.findAll();
        if (rooms.isEmpty()) throw new RoomNotFoundException("⚠️ No rooms found.");
        StringBuilder sb = new StringBuilder("\n=== 🧩 ROOMS ===\n");
        rooms.forEach(r -> sb.append(r).append("\n"));
        return sb.toString();
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = roomDao.findAll();
        if (rooms.isEmpty()) throw new RoomNotFoundException("⚠️ No rooms found.");
        return rooms;
    }

    public Optional<Room> findRoomById(int id) {
        return roomDao.findById(id);
    }

    public String deleteRoomById(int id) {
        Room room = roomDao.findById(id).orElseThrow(() -> new RoomNotFoundException("❌ Room not found."));
        if (!roomDao.remove(room)) {
            throw new DatabaseOperationException("❌ Error deleting room.");
        }

        Optional<EscapeRoom> er = escapeRoomService.findEscapeRoomById(room.getEscapeRoomId());
        String escapeRoomName = er.map(EscapeRoom::getName).orElse("Unknown Escape Room");

        notifyObservers("[UPDATE] Unfortunately the room " + room.getName() +
                " from Escape Room: " + escapeRoomName + " is no longer available.");

        return "🗑️ Room deleted successfully.";
    }

    public String addHint(String description, String theme, int roomId, double price) {
        if (description == null || description.isBlank()) {
            throw new InvalidInputException("❌ Hint description cannot be empty.");
        }

        findRoomById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("❌ Room ID not found."));

        Hint hint = new Hint(description, theme, roomId, price);
        if (!hintDao.save(hint)) {
            throw new DatabaseOperationException("❌ Error saving hint.");
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
            throw new DatabaseOperationException("❌ Error deleting hint.");
        }
        return "🗑️ Hint deleted successfully.";
    }

    public String addDecoration(String name, String material, int roomId, double price) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("❌ Decoration name cannot be empty.");
        }
        if (material == null || material.isBlank()) {
            throw new InvalidInputException("❌ Decoration material cannot be empty.");
        }

        findRoomById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("❌ Room ID not found."));

        Decoration decoration = new Decoration(name, material, price, roomId);
        if (!decorationDao.save(decoration)) {
            throw new DatabaseOperationException("❌ Error saving decoration.");
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
            throw new DatabaseOperationException("❌ Error deleting decoration.");
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

    private Difficulty parseDifficulty(String input) {
        try {
            return Difficulty.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("⚠️ Invalid difficulty. Valid values: EASY, MEDIUM, HARD.");
        }
    }

    private void validateRoomName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("❌ Room name cannot be empty.");
        }
    }

}
