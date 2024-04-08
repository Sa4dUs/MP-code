package server.minions;

import server.nosql.Document;
import server.nosql.Schemas.HumanSchema;

public class Human extends Minion{
    private int loyalty;
    private final int maxLoyalty = 3;

    private enum LoyaltyLevels
    {
        Low, Mid, High
    }
    public Human(){}

    @Override
    public String toString() {
        return super.toString() + " Loyalty:" + LoyaltyLevels.values()[loyalty-1];
    }

    public int getLoyalty() {
        return this.loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = (loyalty > maxLoyalty) ? maxLoyalty : Math.max(loyalty, 1);
    }

    public int getMaxLoyalty(){return  maxLoyalty;}

    @Override
    public Document getDocument()
    {
        Document document = new Document(new HumanSchema());
        document.setProperty("loyalty", this.loyalty);
        document.updateFromDocument(super.getDocument());
        return document;
    }
}
