package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class CharacterSchema extends Schema {
    public CharacterSchema() {
        super(Map.of(
                "name", String.class,
                "breed", Integer.class,
                "health", Integer.class,
                "weaponsList", String[].class,
                "armorList", String[].class,
                "ability", String.class,
                "specialAbility", String.class,
                "debilitiesList", String[].class,
                "resistancesList", String[].class,
                "minionList", String[].class
        ));
        super.schema.put("gold", Integer.class);
    }
}
