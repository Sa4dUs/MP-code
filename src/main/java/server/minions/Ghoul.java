package server.minions;

public class Ghoul extends  Minion{
    private int dependence;
    private final int maxDependence = 5;

    public int getDependence() {
        return dependence;
    }
    public void setDependence(int dependence) {
        this.dependence = (dependence > maxDependence) ? maxDependence : Math.max(dependence, 1);;
    }

    public  int getMaxDependence(){return maxDependence;}
}
