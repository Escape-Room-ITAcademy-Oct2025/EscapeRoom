package model;

public class Room {
    private int id;
    private String name;
    private Difficulty difficulty;
    private double price;

    public Room(int id, String name, Difficulty difficulty, double price) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                "Room Name: " + name + '\'' +
                 ", Difficulty: " + difficulty +
                "Price: " + price;
    }
}
