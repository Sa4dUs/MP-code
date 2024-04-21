package server.nosql;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.ItemSchema;

public class Item implements JSONable {
    private String id;
    private String field;

    public Item() {}
    public Item (String field) {
        this.field = field;
    }

    public String getId() {
        return this.id;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public Document getDocument() {
        Document doc = new Document(new ItemSchema());
        doc.setProperty("field", this.field);
        this.id = doc.getId();
        return doc;
    }
}