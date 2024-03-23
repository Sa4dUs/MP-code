package server.nosql.Schemas;

import java.util.Map;

public class HumanSchema extends MinionSchema {
    public HumanSchema() {
        super();
        this.schema.put("loyalty", Integer.class);
    }
}
