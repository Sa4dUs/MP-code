package server.items;

public class Weapon extends Stats{
    private boolean twoHanded;

    public boolean isTwoHanded() {
        return this.twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }

}
