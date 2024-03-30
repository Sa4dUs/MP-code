package server;

import server.nosql.Document;
import server.nosql.JSONable;

public class Characteristic implements JSONable {

    private String id;
    private String name;
    private int value;
    private final int maxValue = 3;

    @Override
    public String toString() {
        return id;
    }

    public void setValue(int value){ this.value = (value > maxValue) ? maxValue : Math.max(value, 1);}

    public int getValue() {
        return value;
    }

    @Override
    public Document getDocument() {
        return null;
    }
}
