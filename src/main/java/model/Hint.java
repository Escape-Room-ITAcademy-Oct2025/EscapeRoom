package model;

/**
 * Representa una pista (hint) dentro de una sala del Escape Room.
 * Cada pista pertenece a una sala y tiene un tema, descripción y precio.
 */
public class Hint {

    private int id;
    private String description;
    private String theme;
    private int roomId;
    private double price;

    // ----- Constructors -----
    public Hint() {
    }

    public Hint(int id, String description, String theme, int roomId, double price) {
        this.id = id;
        this.description = description;
        this.theme = theme;
        this.roomId = roomId;
        this.price = price;
    }

    // Constructor sin id (para inserts)
    public Hint(String description, String theme, int roomId, double price) {
        this.description = description;
        this.theme = theme;
        this.roomId = roomId;
        this.price = price;
    }

    // ----- Getters & Setters -----
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ----- toString() -----
    @Override
    public String toString() {
        return "Hint{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", theme='" + theme + '\'' +
                ", roomId=" + roomId +
                ", price=" + price +
                '}';
    }
}
