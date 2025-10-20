package service;

import dao.EscapeRoomDaoImpl;
import model.EscapeRoom;

import java.util.List;

public class EscapeRoomService {

    private final EscapeRoomDaoImpl escapeRoomDao = new EscapeRoomDaoImpl();

    public void createEscapeRoom(EscapeRoom escapeRoom) {
        escapeRoomDao.save(escapeRoom);
    }

    public List<EscapeRoom> findAllEscapeRooms() {
        return escapeRoomDao.findAll();
    }

    public EscapeRoom findEscapeRoomById(int id) {
        return escapeRoomDao.findById(id);
    }

    public void deleteEscapeRoom(EscapeRoom escapeRoom) {
        escapeRoomDao.remove(escapeRoom);
    }
}
