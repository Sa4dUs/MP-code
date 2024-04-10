package server.minions;

import server.nosql.Document;
import server.nosql.Schemas.GhoulSchema;

public class Ghoul extends  Minion{
    private int dependence;
    private final int maxDependence = 5;
    public Ghoul(){}

    @Override
    public String toString() {
        return super.toString() + " Dependence:" + this.dependence;
    }

    public int getDependence() {
        return dependence;
    }

    public void setDependence(int dependence) {
        this.dependence = (dependence > maxDependence) ? maxDependence : Math.max(dependence, 1);;
    }

    public  int getMaxDependence(){return maxDependence;}

    @Override
    public Document getDocument()
    {
        Document document = new Document(new GhoulSchema());
        document.updateFromDocument(super.getDocument());
        document.setProperty("dependence", this.dependence);
        return document;
    }
}
