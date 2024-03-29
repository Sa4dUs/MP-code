package server.minions;

import server.nosql.Document;

public class Human extends Minion{
    private int loyalty;
    private final int maxLoyalty = 3;

    public Human(){}

    public Human(Document doc)
    {
        super(doc);
        Document.setFieldsFromDocument(this, doc);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getLoyalty() {
        return this.loyalty;
    }
    public void setLoyalty(int loyalty) {
        this.loyalty = (loyalty > maxLoyalty) ? maxLoyalty : Math.max(loyalty, 1);
    }

    public int getMaxLoyalty(){return  maxLoyalty;}
}
