package service;

import dao.DecorationDaoImpl;
import dao.HintDaoImpl;
import dao.RoomDaoImpl;
import model.Decoration;
import model.Hint;
import model.Room;

import java.util.List;

/**
 * InventoryService centraliza la gestión de todos los elementos físicos o jugables
 * del Escape Room: salas, decoraciones y pistas.
 *
 * Combina la información de varios DAOs y proporciona lógica de negocio
 * como cálculos de valor total, filtrado o disponibilidad.
 */
public class InventoryService {

    private final RoomDaoImpl roomDao;
    private final DecorationDaoImpl decorationDao;
    private final HintDaoImpl hintDao;

    public InventoryService() {
        this.roomDao = new RoomDaoImpl();
        this.decorationDao = new DecorationDaoImpl();
        this.hintDao = new HintDaoImpl();
    }

    // ----- Rooms -----
    public List<Room> findAllRooms() {
        return roomDao.findAll();
    }

    public Room findRoomById(int id) {
        return roomDao.findById(id);
    }

    public void saveRoom(Room room) {
        roomDao.save(room);
    }

    public void removeRoom(Room room) {
        roomDao.remove(room);
    }

    // ----- Decorations -----
    public List<Decoration> findAllDecorations() {
        return decorationDao.findAll();
    }

    public Decoration findDecorationById(int id) {
        return decorationDao.findById(id);
    }

    public void saveDecoration(Decoration decoration) {
        decorationDao.save(decoration);
    }

    public void removeDecoration(Decoration decoration) {
        decorationDao.remove(decoration);
    }

    // ----- Hints -----
    public List<Hint> findAllHints() {
        return hintDao.findAll();
    }

    public List<Hint> findHintsByRoom(int roomId) {
        return hintDao.findByRoomId(roomId);
    }

    public Hint findHintById(int id) {
        return hintDao.findById(id);
    }

    public void saveHint(Hint hint) {
        hintDao.save(hint);
    }

    public void removeHint(Hint hint) {
        hintDao.remove(hint);
    }

    // ----- Logic -----
    /**
     * Calcula el valor total del inventario sumando el precio
     * de todas las decoraciones y las salas disponibles.
     */
    public double calculateTotalInventoryValue() {
        double decorationsValue = findAllDecorations().stream()
                .mapToDouble(Decoration::getPrice)
                .sum();

        // Asegúrate de que en Room tienes getPrice(), no getValue()
        double roomsValue = findAllRooms().stream()
                .mapToDouble(Room::getPrice)
                .sum();

        return decorationsValue + roomsValue;
    }
}
