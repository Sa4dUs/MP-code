package server.items;

public class Weapon extends Stats{
    public boolean isTwoHanded() {
        return twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }

    private boolean twoHanded;
}
