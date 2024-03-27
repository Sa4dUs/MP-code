package server;

public class Characteristic {

    private String id;
    private String name;
    private int value;
    private final int maxValue = 3;

    @Override
    public String toString() {
        return id;
    }

    public void setValue(int value){ this.value = (value > maxValue) ? maxValue : Math.max(value, 1);}
}
