package server.nosql.Schemas;

import server.items.Armor;
import server.nosql.Schema;

import java.util.Map;

public class ChallengeRequestSchema extends Schema {
    public ChallengeRequestSchema(){
        super(Map.of(
                "attackerId", String.class,
                "attackedId", String.class,
                "bet", Integer.class
        ));
    }
}
