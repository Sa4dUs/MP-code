package server.nosql.Schemas;

import java.util.Map;

public class PlayerCharacterSchema extends CharacterSchema {
    public PlayerCharacterSchema() {
        super();
        this.schema.put("gold", Integer.class);
        this.schema.put("activeWeaponL", String.class);
        this.schema.put("activeWeaponR", String.class);
        this.schema.put("activeArmor", String.class);
        this.schema.put("activeNormalAbility", String.class);
        this.schema.put("activeSpecialAbility", String.class);
    }
}
