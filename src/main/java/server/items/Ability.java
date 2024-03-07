package server.items;

public class Ability extends Stats{
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    private int cost;
}
