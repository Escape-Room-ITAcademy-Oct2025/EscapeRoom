package service;

import dao.DecorationDaoImpl;
import dao.HintDaoImpl;
import dao.RoomDaoImpl;
import exception.DecorationNotFoundException;
import exception.HintNotFoundException;
import exception.RoomNotFoundException;
import model.Decoration;
import model.Hint;
import model.Room;

import java.util.List;


public class InventoryService {

    private final RoomDaoImpl roomDao;
    private final HintDaoImpl hintDao;
    private final DecorationDaoImpl decorationDao;

    public InventoryService() {
        this.roomDao = new RoomDaoImpl();
        this.hintDao = new HintDaoImpl();
        this.decorationDao = new DecorationDaoImpl();
    }

    public boolean saveRoom(Room room) {
        return roomDao.save(room);
    }

    public List<Room> findAllRooms() {
        return roomDao.findAll();
    }

    public Room findRoomById(int id) {
        return roomDao.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID: " + id + " was not found"));
    }

    public boolean removeRoom(Room room) {
        return roomDao.remove(room);
    }

    public boolean removeRoomById(int id) {
        Room room = roomDao.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID: " + id + " was not found"));
        return roomDao.remove(room);
    }

    public boolean saveHint(Hint hint) {
        return hintDao.save(hint);
    }

    public List<Hint> findAllHints() {
        return hintDao.findAll();
    }

    public Hint findHintById(int id) {
        return hintDao.findById(id)
                .orElseThrow(() -> new HintNotFoundException("Hint with ID: " + id + " was not found"));
    }

    public boolean removeHint(Hint hint) {
        return hintDao.remove(hint);
    }

    public boolean removeHintById(int id) {
        Hint hint = hintDao.findById(id)
                .orElseThrow(() -> new HintNotFoundException("Hint with ID: " + id + " was not found"));
        return hintDao.remove(hint);
    }

    public boolean saveDecoration(Decoration decoration) {
        return decorationDao.save(decoration);
    }

    public List<Decoration> findAllDecorations() {
        return decorationDao.findAll();
    }

    public Decoration findDecorationById(int id) {
        return decorationDao.findById(id)
                .orElseThrow(() -> new DecorationNotFoundException("Decoration with ID: " + id + " was not found"));
    }

    public boolean removeDecoration(Decoration decoration) {
        return decorationDao.remove(decoration);
    }

    public boolean removeDecorationById(int id) {
        Decoration dec = decorationDao.findById(id)
                .orElseThrow(() -> new DecorationNotFoundException("Decoration with ID: " + id + " was not found"));
        return decorationDao.remove(dec);
    }

    public double calculateTotalInventoryValue() {
        double totalRooms = roomDao.findAll().stream().mapToDouble(Room::getPrice).sum();
        double totalHints = hintDao.findAll().stream().mapToDouble(Hint::getPrice).sum();
        double totalDecorations = decorationDao.findAll().stream().mapToDouble(Decoration::getPrice).sum();
        return totalRooms + totalHints + totalDecorations;
    }
}
