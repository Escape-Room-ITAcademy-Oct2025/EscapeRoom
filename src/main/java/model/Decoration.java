package model;

/**
 * Representa un objeto de decoración dentro de una sala del Escape Room.
 * Cada decoración tiene un nombre, material, precio y pertenece a una sala.
 */
public class Decoration {

    private int id;
    private String name;
    private String material;
    private double price;
    private int roomId;

    // ----- Constructors -----
    public Decoration() {
    }

    public Decoration(int id, String name, String material, double price, int roomId) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.price = price;
        this.roomId = roomId;
    }

    // Constructor sin id (para inserts)
    public Decoration(String name, String material, double price, int roomId) {
        this.name = name;
        this.material = material;
        this.price = price;
        this.roomId = roomId;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    // ----- toString() -----
    @Override
    public String toString() {
        return "Decoration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", material='" + material + '\'' +
                ", price=" + price +
                ", roomId=" + roomId +
                '}';
    }
}
