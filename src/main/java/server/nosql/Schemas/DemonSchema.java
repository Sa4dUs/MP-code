package server.nosql.Schemas;

public class DemonSchema extends MinionSchema {
    public DemonSchema() {
        super();
        this.schema.put("pact", String.class);
        this.schema.put("minions", String[].class);
    }
}
