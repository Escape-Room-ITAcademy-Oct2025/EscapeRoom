package model;

import java.time.LocalDate;

public class Ticket {
    private int id;
    private int playerId;
    private int roomId;
    private LocalDate purchaseDate;
    private double price;

    public Ticket(int id, int playerId, int roomId, LocalDate purchaseDate, double price){
        this.id = id;
        this.playerId = playerId;
        this.roomId = roomId;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
