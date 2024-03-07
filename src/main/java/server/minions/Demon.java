package server.minions;

import java.util.List;

public class Demon extends Minion{
    public String getPact() {
        return pact;
    }

    public void setPact(String pact) {
        this.pact = pact;
    }

    private String pact;

    public List<Minion> getMinions() {
        return minions;
    }

    public void setMinions(List<Minion> minions) {
        this.minions = minions;
    }

    private List<Minion> minions;

    public int getMinionsHealth() {
        int totalHealth = 0;
        for (Minion m: minions) {
            totalHealth += m.getHealth();
        }
        return totalHealth;
    }
}
