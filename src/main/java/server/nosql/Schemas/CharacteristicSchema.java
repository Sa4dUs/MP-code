package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class CharacteristicSchema extends Schema {
    public CharacteristicSchema() {
        super(Map.of(
            "name", String.class,
            "value", Integer.class
        ));
    }
}
