package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class ItemSchema extends Schema {
    public ItemSchema() {
        super();
        this.schema.put("field", String.class);
    }
}
