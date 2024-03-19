package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class UserSchema extends Schema {
    public UserSchema() {
        super(Map.of(
                "username", String.class,
                "password", String.class
        ));
    }
}