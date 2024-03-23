package server.nosql.Schemas;

import java.util.Map;

public class PlayerCharacterSchema extends CharacterSchema {
    public PlayerCharacterSchema() {
        super();
        this.schema.put("gold", Integer.class);
        this.schema.put("weaponId(LWeapon)", String.class);
        this.schema.put("characteristicId(D)", String[].class);
        this.schema.put("characteristicId(S)", String[].class);
        this.schema.put("weaponId(RWeapon)", String.class);
        this.schema.put("armorId", String.class);
        this.schema.put("abilityId(N)", String.class);
        this.schema.put("abilityId(S)", String.class);
    }
}
