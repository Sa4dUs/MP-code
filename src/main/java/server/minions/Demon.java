package server.minions;

import java.util.List;

public class Demon extends Minion{
    private String pact;
    private List<Minion> minions;

    public String getPact() {
        return this.pact;
    }

    public void setPact(String pact) {
        this.pact = pact;
    }


    public List<Minion> getMinions() {
        return this.minions;
    }

    public void setMinions(List<Minion> minions) {
        this.minions = minions;
    }

    public int getMinionsHealth() {
        int totalHealth = 0;
        for (Minion m: this.minions) {
            totalHealth += m.getHealth();
        }
        return totalHealth;
    }
}
