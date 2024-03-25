package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class CharacterSchema extends Schema {
    public CharacterSchema() {
        super(Map.of(
                "name", String.class,
                "breed", Integer.class,
                "hp", Integer.class,
                "weaponId", String[].class,
                "armorId", String[].class,
                "abilityId(N)", String[].class,
                "abilityId(S)", String[].class,
                "characteristicId(D)", String[].class,
                "characteristicId(S)", String[].class,
                "minionId", String[].class
        ));
    }
}
