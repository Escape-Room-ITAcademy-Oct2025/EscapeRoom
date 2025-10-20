package model;

import java.util.ArrayList;
import java.util.List;

public class EscapeRoom {
    private int id;
    private String name;
    private final List<Room> rooms = new ArrayList<>();

    public EscapeRoom() {}

    public EscapeRoom(String name) {
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Room> getRooms() { return rooms; }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public String toString() {
        return "EscapeRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms.size() +
                '}';
    }
}
