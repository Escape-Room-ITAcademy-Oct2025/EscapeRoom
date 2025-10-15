package model;

import java.time.LocalDate;

public class Reward {
    private int id;
    private int playerId;
    private LocalDate date;
    private String description;

    public Reward(int id, int playerId, LocalDate date, String description) {
        this.id = id;
        this.playerId = playerId;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
