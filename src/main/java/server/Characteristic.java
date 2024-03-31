package server;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.CharacteristicSchema;

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
    public String getName() {return this.name;}

    @Override
    public Document getDocument() {
        Document document = new Document(new CharacteristicSchema());
        if(this.id != null)
            document.setProperty("id", this.id);
        else
            this.id = document.getId();
        document.setProperty("name", this.name);
        document.setProperty("value", this.value);

        return document;
    }
}
