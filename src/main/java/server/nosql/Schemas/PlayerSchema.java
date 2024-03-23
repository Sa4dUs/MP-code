package server.nosql.Schemas;

import java.util.Map;

public class PlayerSchema extends UserSchema {
    public PlayerSchema() {
        super();
        this.schema.put("playerCharacterId", String.class);
        this.schema.put("pendingDuelId", String[].class);
        this.schema.put("duelResultID", String[].class);
    }
}
