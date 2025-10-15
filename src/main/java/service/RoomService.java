package service;

import model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private List<Room> rooms;

    public RoomService() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Added Room: " + room);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public boolean removeRoomById(int id) {
        return rooms.removeIf(room -> room.getId() == id);
    }
}


