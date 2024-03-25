package server;

public class Characteristic {

    private String name;
    private int value;
    private final int maxValue = 3;

    public void setValue(int value){ this.value = (value > maxValue) ? maxValue : Math.max(value, 1);}
}
