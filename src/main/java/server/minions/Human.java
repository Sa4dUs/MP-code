package server.minions;

public class Human extends Minion{
    private int loyalty = 1;
    private final int maxLoyalty = 3;

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
