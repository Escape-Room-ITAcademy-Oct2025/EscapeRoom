package model;

public class Hint {
    private int id;
    private String description;
    private HintType hintType;
    private double cost;
    private int roomId;

    public Hint(int id, String description, HintType hintType, double cost, int roomId) {
        this.id = id;
        this.description = description;
        this.hintType = hintType;
        this.cost = cost;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public HintType getHintType() {
        return hintType;
    }

    public double getCost() {
        return cost;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHintType(HintType hintType) {
        this.hintType = hintType;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Hint: " +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", type=" + hintType +
                ", cost=" + cost +
                ", roomId=" + roomId;
    }
}
