package server.minions;

import server.nosql.Document;

public class Ghoul extends  Minion{
    private int dependence;
    private final int maxDependence = 5;

    public Ghoul(){}

    public Ghoul(Document doc)
    {
        super(doc);
        Document.setFieldsFromDocument(this, doc);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    public int getDependence() {
        return dependence;
    }
    public void setDependence(int dependence) {
        this.dependence = (dependence > maxDependence) ? maxDependence : Math.max(dependence, 1);;
    }

    public  int getMaxDependence(){return maxDependence;}
}
