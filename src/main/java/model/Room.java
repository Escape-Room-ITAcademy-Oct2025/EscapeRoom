package model;

/**
 * Representa una sala (room) del Escape Room.
 * Cada sala tiene un nombre, una dificultad y un precio base.
 */
public class Room {

    private int id;
    private String name;
    private Difficulty difficulty; // podría ser Enum si quieres, pero String es suficiente
    private double price;
    private int escapeRoomId;

    // ----- Constructors -----
    public Room() {
    }

    public Room(int id, String name, Difficulty difficulty, double price) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
    }

    // Constructor sin ID (para inserts)
    public Room(String name, Difficulty difficulty, double price) {
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
    }

    // ----- Getters & Setters -----
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
