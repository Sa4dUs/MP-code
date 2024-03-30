package server.minions;

import server.Database;
import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.MinionSchema;

import java.lang.reflect.Field;

public abstract class Minion implements JSONable {
    private String id;
    private String name = "Undefined";
    private int health = 1;
    private final int maxHealth = 3;

    public Minion(){}
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
        this.health = (health > maxHealth) ? maxHealth : Math.max(health, 1);
    }

    @Override
    public Document getDocument()
    {
        Document document = new Document(new MinionSchema());
        document.setProperty("id", this.id);
        return document;
    }
}
