package server.minions;

public class Human extends Minion{
    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    private int loyalty;
}
