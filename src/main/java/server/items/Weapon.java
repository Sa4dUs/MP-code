package server.items;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.AbilitySchema;
import server.nosql.Schemas.WeaponSchema;

public class Weapon extends Stats {

    public Weapon(){}
    private boolean twoHanded;
    @Override
    public String toString() {
        return super.toString() + (this.twoHanded ? "Two handed" : "One handed");
    }

    public boolean isTwoHanded() {
        return this.twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }

    @Override
    public Document getDocument() {
        Document document = new Document(new WeaponSchema());
        document.setProperty("twoHanded", this.twoHanded);
        document.updateFromDocument(super.getDocument());
        return document;
    }
}
