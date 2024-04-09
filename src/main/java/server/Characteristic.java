package server;

import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;
import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.CharacteristicSchema;

import java.util.ArrayList;
import java.util.List;

public abstract class Characteristic implements JSONable {

    private String id;
    private String name;
    private int value;
    private final int maxValue = 3;

    public enum CharacteristicType
    {
        Weakness, Resistance
    }

    private static final List<Class<? extends Characteristic>> subClasses = new ArrayList<>();

    static {
        subClasses.add(Resistance.class);
        subClasses.add(Weakness.class);
    }

    public static List<Class<? extends Characteristic>> getSubClasses() {
        return subClasses;
    }
    @Override
    public String toString() {
        return this.name + " Impact:" + this.value;
    }

    public void setValue(int value){ this.value = (value > maxValue) ? maxValue : Math.max(value, 1);}

    public int getValue() {
        return value;
    }
    public String getName() {return this.name;}

    @Override
    public Document getDocument() {
        Document document = new Document(new CharacteristicSchema());
        if (this.id != null) {
            document.setProperty("id", this.id);
        } else {
            this.id = document.getId();
        }
        document.setProperty("name", this.name);
        document.setProperty("value", this.value);

        return document;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
