package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class PlayerSchema extends Schema {
    public PlayerSchema() {
        super(Map.of(
                "username", String.class,
                "password", String.class
        ));
    }
}
