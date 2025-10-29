package model;

import model.observer.Observer;

public class Player implements Observer {

    private int id;
    private String name;
    private String email;
    private boolean subscribed;

    public Player() {
    }

    public Player(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subscribed = subscribed;
    }

    public Player(String name, String email) {
        this.name = name;
        this.email = email;
        this.subscribed = subscribed;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public void update(String eventMessage) {
        System.out.println("📢 Notification for " + name + ": " + eventMessage);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subscribed='" + subscribed +
                '}';
    }
}
