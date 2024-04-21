package server.items;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.StatsSchema;

public abstract class Stats implements JSONable {
    private String id;
    private String name;
    private int attack;
    private final int maxAttack = 3;
    private int defense;
    private final int maxDefense = 3;


    public Stats(){}

    public Stats(String name, int attack, int defense) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }
    
    @Override
    public String toString()
    {
        return this.name + " ATK:" + this.attack + " DFS:" + this.defense;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = (attack > maxAttack) ? maxAttack : Math.max(attack, 0);;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense = (defense > maxDefense) ? maxDefense : Math.max(defense, 0);;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Document getDocument()
    {
        Document document = new Document(new StatsSchema());
        if(this.id != null)
            document.setProperty("id", this.id);
        else
            this.id = document.getId();
        document.setProperty("attack", this.attack);
        document.setProperty("defense", this.defense);
        document.setProperty("name", this.name);

        return document;
    }

}
