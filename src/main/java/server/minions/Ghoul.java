package server.minions;

public class Ghoul extends  Minion{
    public int getDependence() {
        return dependence;
    }

    public void setDependence(int dependence) {
        this.dependence = dependence;
    }

    private int dependence;
}
