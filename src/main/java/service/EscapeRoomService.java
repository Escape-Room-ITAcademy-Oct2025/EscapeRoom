package service;

import dao.EscapeRoomDaoImpl;
import exception.*;
import model.EscapeRoom;
import model.observer.Observer;
import model.observer.Subject;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


public class EscapeRoomService implements Subject {

    private static EscapeRoomService instance;

    public static EscapeRoomService getInstance() {
        if (instance == null) {
            instance = new EscapeRoomService();
        }
        return instance;
    }

    private EscapeRoomService() {
        this.escapeRoomDao = new EscapeRoomDaoImpl();
    }

    private final EscapeRoomDaoImpl escapeRoomDao;
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

    public String createEscapeRoom(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException("❌ Escape Room name cannot be empty.");
        }

        EscapeRoom escapeRoom = new EscapeRoom(name);
        boolean created = escapeRoomDao.save(escapeRoom);

        if (!created) {
            throw new OperationFailedException("❌ Error creating Escape Room. Please try again.");
        }

        notifyObservers("[NEW] We are happy to announce that a new escape room has been created: " +
                escapeRoom.getName());

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

        notifyObservers("[UPDATE] Unfortunately the escape room " + er.getName() +
                " has been deleted");

        return "🗑️ Escape Room '" + er.getName() + "' and its associated rooms were deleted successfully.";
    }
}
