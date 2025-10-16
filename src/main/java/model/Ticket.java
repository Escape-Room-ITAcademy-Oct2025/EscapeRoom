package model;

import java.time.LocalDateTime;

/**
 * Representa un ticket de entrada a una sala del Escape Room.
 */
public class Ticket {

    private int id;
    private int playerId;
    private int roomId;
    private LocalDateTime purchaseDate;
    private double price;

    // ----- Constructors -----
    public Ticket() {
    }

    public Ticket(int id, int playerId, int roomId, LocalDateTime purchaseDate, double price) {
        this.id = id;
        this.playerId = playerId;
        this.roomId = roomId;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }

    public Ticket(int playerId, int roomId, double price) {
        this.playerId = playerId;
        this.roomId = roomId;
        this.price = price;
        this.purchaseDate = LocalDateTime.now();
    }

    // ----- Getters & Setters -----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", roomId=" + roomId +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                '}';
    }
}
