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

    // ----- Decorations -----
    public List<Decoration> findAllDecorations() {
        return decorationDao.findAll();
    }

    // ----- Hints -----
    public List<Hint> findAllHints() {
        return hintDao.findAll();
    }

    public List<Hint> findHintsByRoom(int roomId) {
        return hintDao.findByRoomId(roomId);
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
