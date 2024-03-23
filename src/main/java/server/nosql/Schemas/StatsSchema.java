package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class StatsSchema extends Schema {
    public StatsSchema() {
        super(Map.of(
                "atk", Integer.class,
                "dfs", Integer.class
        ));
    }
}