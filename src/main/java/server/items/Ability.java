package server.items;

public class Ability extends Stats{
    private int cost;

    @Override
    public String toString() {
        return super.toString();
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

}
