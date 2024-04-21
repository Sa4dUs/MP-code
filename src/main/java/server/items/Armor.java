package server.items;

import server.nosql.Document;

public class Armor extends Stats {

    public Armor(){}

    public Armor(String name, int attack, int defense){super(name, attack, defense);}
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Document getDocument() {
        return super.getDocument();
    }
}
