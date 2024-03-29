package server.nosql.Schemas;

import java.util.Map;

public class PlayerSchema extends UserSchema {
    public PlayerSchema() {
        super();
        this.schema.put("character", String.class);
        this.schema.put("pendingDuels", String[].class);
        this.schema.put("results", String[].class);
        this.schema.put("blocked", Boolean.class);
        this.schema.put("pending", Boolean.class);
    }
}
