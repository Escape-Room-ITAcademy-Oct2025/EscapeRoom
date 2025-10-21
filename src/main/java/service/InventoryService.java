package service;

import dao.DecorationDaoImpl;
import dao.HintDaoImpl;
import dao.RoomDaoImpl;
import model.Decoration;
import model.Hint;
import model.Room;

import java.util.List;
import java.util.Optional;

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
        Optional<Room> roomOpt = roomDao.findById(id);
        return roomOpt.orElse(null);
    }

    public boolean removeRoom(Room room) {
        return roomDao.remove(room);
    }

    public boolean removeRoomById(int id) {
        Optional<Room> roomOpt = roomDao.findById(id);
        return roomOpt.map(roomDao::remove).orElse(false);
    }

    public boolean saveHint(Hint hint) {
        return hintDao.save(hint);
    }

    public List<Hint> findAllHints() {
        return hintDao.findAll();
    }

    public Hint findHintById(int id) {
        Optional<Hint> hintOpt = hintDao.findById(id);
        return hintOpt.orElse(null);
    }

    public boolean removeHint(Hint hint) {
        return hintDao.remove(hint);
    }

    public boolean removeHintById(int id) {
        Optional<Hint> hintOpt = hintDao.findById(id);
        return hintOpt.map(hintDao::remove).orElse(false);
    }

    public boolean saveDecoration(Decoration decoration) {
        return decorationDao.save(decoration);
    }

    public List<Decoration> findAllDecorations() {
        return decorationDao.findAll();
    }

    public Decoration findDecorationById(int id) {
        Optional<Decoration> decOpt = decorationDao.findById(id);
        return decOpt.orElse(null);
    }

    public boolean removeDecoration(Decoration decoration) {
        return decorationDao.remove(decoration);
    }

    public boolean removeDecorationById(int id) {
        Optional<Decoration> decOpt = decorationDao.findById(id);
        return decOpt.map(decorationDao::remove).orElse(false);
    }

    public double calculateTotalInventoryValue() {
        double totalRooms = roomDao.findAll().stream().mapToDouble(Room::getPrice).sum();
        double totalHints = hintDao.findAll().stream().mapToDouble(Hint::getPrice).sum();
        double totalDecorations = decorationDao.findAll().stream().mapToDouble(Decoration::getPrice).sum();
        return totalRooms + totalHints + totalDecorations;
    }
}
