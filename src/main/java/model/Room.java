package model;

public class Room {

    private int id;
    private String name;
    private Difficulty difficulty;
    private double price;
    private int escapeRoomId;

    public Room() {
    }

    public Room(int id, String name, Difficulty difficulty, double price) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
    }

    public Room(String name, Difficulty difficulty, double price) {
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getEscapeRoomId() {
        return escapeRoomId;
    }

    public void setEscapeRoomId(int escapeRoomId) {
        this.escapeRoomId = escapeRoomId;
    }

    // ----- toString() -----
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", price=" + price +
                ", escapeRoomId" + escapeRoomId +
                '}';
    }
}
