package server.items;

import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;
import server.nosql.Document;
import server.nosql.Schemas.AbilitySchema;

import java.util.ArrayList;
import java.util.List;

public abstract class Ability extends Stats{
    private int cost;

    public enum AbilityType
    {
        Blessing, Discipline, Talent
    }

    private static final List<Class<? extends Ability>> subClasses = new ArrayList<>();

    static {
        subClasses.add(Blessing.class);
        subClasses.add(Discipline.class);
        subClasses.add(Talent.class);
    }

    public static List<Class<? extends Ability>> getSubClasses() {
        return subClasses;
    }

    public Ability(){}

    public Ability(String name, int attack, int defense, int cost)
    {
        super(name, attack, defense);
        this.cost = cost;
    }
    @Override
    public String toString() {
        return super.toString() + " Cost:" + this.cost;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public Document getDocument() {
        Document document = new Document(new AbilitySchema());
        document.setProperty("cost", this.cost);
        document.updateFromDocument(super.getDocument());
        return document;
    }

    public AbilityType getType() {
        return null;
    }
}
