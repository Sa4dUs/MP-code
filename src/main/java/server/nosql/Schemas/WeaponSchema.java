package server.nosql.Schemas;

import java.util.Map;

public class WeaponSchema extends StatsSchema {
    public WeaponSchema() {
        super();
        this.schema.put("twoHanded", Boolean.class);
    }
}
