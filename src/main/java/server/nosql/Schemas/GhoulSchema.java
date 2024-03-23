package server.nosql.Schemas;

import java.util.Map;

public class GhoulSchema extends MinionSchema {
    public GhoulSchema() {
        super();
        this.schema.put("dependence", Integer.class);
    }
}
