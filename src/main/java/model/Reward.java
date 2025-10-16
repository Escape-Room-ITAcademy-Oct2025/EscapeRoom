package model;

import java.time.LocalDateTime;

/**
 * Representa una recompensa ganada por un jugador.
 */
public class Reward {

    private int id;
    private int playerId;
    private String name;
    private String description;
    private LocalDateTime dateAwarded;

    public Reward() {
    }

    public Reward(int id, int playerId, String name, String description, LocalDateTime dateAwarded) {
        this.id = id;
        this.playerId = playerId;
        this.name = name;
        this.description = description;
        this.dateAwarded = dateAwarded;
    }

    public Reward(int playerId, String name, String description) {
        this.playerId = playerId;
        this.name = name;
        this.description = description;
        this.dateAwarded = LocalDateTime.now();
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateAwarded() { return dateAwarded; }
    public void setDateAwarded(LocalDateTime dateAwarded) { this.dateAwarded = dateAwarded; }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateAwarded=" + dateAwarded +
                '}';
    }
}
