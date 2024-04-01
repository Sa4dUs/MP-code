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
    @Override
    public String toString()
    {
        return id;
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
        this.attack = (attack > maxAttack) ? maxAttack : Math.max(attack, 1);;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense = (defense > maxDefense) ? maxDefense : Math.max(defense, 1);;
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
