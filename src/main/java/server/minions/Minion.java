package server.minions;

public abstract class Minion {
    private String id;
    private String name = "Undefined";
    private int health = 1;
    private final int maxHealth = 3;

    @Override
    public String toString()
    {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "Unnamed";
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = (health > maxHealth) ? maxHealth : Math.max(health, 1);;
    }
}
