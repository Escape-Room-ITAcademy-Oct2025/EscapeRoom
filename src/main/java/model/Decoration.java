package model;

public class Decoration {
    private int id;
    private String type;
    private double cost;

    public Decoration(int id, String type, double cost) {
        this.id = id;
        this.type = type;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Decoration id: " + id + '\'' +
                ", Type: " + type +
                ", Cost: " + cost;
    }

}
