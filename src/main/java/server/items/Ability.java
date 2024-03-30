package server.items;

import server.nosql.Document;
import server.nosql.Schemas.AbilitySchema;

public class Ability extends Stats{
    private int cost;

    public Ability(){}
    @Override
    public String toString() {
        return super.toString();
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
}
