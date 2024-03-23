package server.nosql.Schemas;

import java.util.Map;

public class AbilitySchema extends StatsSchema {
    public AbilitySchema() {
        super();
        this.schema.put("cost", Integer.class);
    }
}
