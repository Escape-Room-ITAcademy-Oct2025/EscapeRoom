package service;

import dao.EscapeRoomDaoImpl;
import model.EscapeRoom;

import java.util.List;
import java.util.Optional;

public class EscapeRoomService {

    private final EscapeRoomDaoImpl escapeRoomDao = new EscapeRoomDaoImpl();

    public boolean createEscapeRoom(EscapeRoom escapeRoom) {
        return escapeRoomDao.save(escapeRoom);
    }

    public List<EscapeRoom> findAllEscapeRooms() {
        return escapeRoomDao.findAll();
    }

    public Optional<EscapeRoom> findEscapeRoomById(int id) {
        return escapeRoomDao.findById(id);
    }

    public boolean deleteEscapeRoom(EscapeRoom escapeRoom) {
        return escapeRoomDao.remove(escapeRoom);
    }
}
