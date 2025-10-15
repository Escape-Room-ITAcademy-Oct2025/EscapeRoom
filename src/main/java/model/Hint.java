package model;

public class Hint {
    private int id;
    private String description;
    private HintType hintType;
    private int roomId;

    public Hint(int id, String description, HintType hintType, int roomId) {
        this.id = id;
        this.description = description;
        this.hintType = hintType;
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

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Hint: " +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", type=" + hintType +
                ", roomId=" + roomId;
    }
}
