package server.minions;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.DemonSchema;

import java.util.ArrayList;
import java.util.List;

public class Demon extends Minion{
    private String pact;
    private List<Minion> minions = new ArrayList<>();

    public Demon(){}

    @Override
    public String toString() {
        return super.toString() + " Pact:" + this.pact + " Minions:" + this.minions.size();
    }

    public int calculateMinionsKilledAfterDamage(int damage)
    {
        int damageLeft = damage - super.getHealth();

        if(damageLeft < 0)
            return 0;

        int minionsKilled = 1;

        for(Minion minion: minions)
        {
            damageLeft -= minion.getHealth();

            if(minion.getClass() == Demon.class)
                minionsKilled += ((Demon) minion).calculateMinionsKilledAfterDamage(damageLeft);
            else
                ++ minionsKilled;

            if(damageLeft <= 0)
                return minionsKilled;
        }

        return minionsKilled;
    }

    public String getPact() {
        return this.pact;
    }

    public void setPact(String pact) {
        this.pact = pact != null ? pact: "Undefined";
    }

    public int getMinionCount()
    {
        int res = 1;

        if(minions.isEmpty())
            return res;

        for(Minion minion: minions)
        {
            if(minion.getClass() == Demon.class)
                res += ((Demon) minion).getMinionCount();
            else
                ++ res;
        }

        return res;
    }

    public List<Minion> getMinions() {
        return this.minions;
    }

    public void addMinion(Minion minion){
        this.minions.add(minion);
    }

    public void setMinions(List<Minion> minions) {
        this.minions = minions;
    }

    private int getMinionsHealth() {
        int totalHealth = 0;
        for (Minion m: this.minions) {
            totalHealth += m.getHealth();
        }
        return totalHealth;
    }

    @Override
    public int getHealth(){
        return super.getHealth() + getMinionsHealth();
    }

    @Override
    public Document getDocument()
    {
        Document document = new Document(new DemonSchema());
        document.setProperty("pact", this.pact);
        document.setProperty("minions", getIdArrayFromArray(minions.toArray(new Minion[0])));
        document.updateFromDocument(super.getDocument());
        return document;
    }

    private String[] getIdArrayFromArray(JSONable[] objects)
    {
        List<String> res = new ArrayList<>();
        for(JSONable object: objects)
        {
            Document objectDoc = object.getDocument();
            objectDoc.saveToDatabase(object.getClass());
            res.add(objectDoc.getId());
        }

        return res.toArray(new String[0]);
    }
}
