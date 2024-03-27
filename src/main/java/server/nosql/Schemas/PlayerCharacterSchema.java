package server.nosql.Schemas;

import java.util.Map;

public class PlayerCharacterSchema extends CharacterSchema {
    public PlayerCharacterSchema() {
        super();
        this.schema.put("gold", Integer.class);
        this.schema.put("weaponId(LWeapon)", String.class);
        this.schema.put("weaponId(RWeapon)", String.class);
        this.schema.put("armorId(Active)", String.class);
        this.schema.put("abilityId(ActiveN)", String.class);
        this.schema.put("abilityId(ActiveS)", String.class);
    }
}
