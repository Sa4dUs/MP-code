package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class MinionSchema extends Schema {
    public MinionSchema() {
        super(Map.of(
                "name", String.class,
                "hp", Integer.class
        ));
    }
}
