package model;

public enum RewardType {
    HONOR("Medal of Honor", "For solving the greatest number of puzzles with brilliance."),
    UNITY("Medal of Unity", "For outstanding teamwork and leadership."),
    PERSISTENCE("Medal of Persistence", "For never giving up despite the obstacles."),
    CREATIVITY("Medal of Creativity", "For solving challenges in unexpected and clever ways."),
    STONE("Medal of Stone", "For trying bravely, even without solving any puzzle.");

    private final String name;
    private final String description;

    RewardType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + " — " + description;
    }
}
