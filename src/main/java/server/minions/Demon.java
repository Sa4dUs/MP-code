package server.minions;

import java.util.ArrayList;
import java.util.List;

public class Demon extends Minion{
    private String pact;
    private List<Minion> minions = new ArrayList<>();

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
        this.pact = pact;
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
}
