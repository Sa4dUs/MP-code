package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class ChallengeResultSchema extends Schema {
        public ChallengeResultSchema(){
        super(Map.of(
                "attackerId", String.class,
                "attackerCharacterId", String.class,
                "attackedCharacterId", String.class,
                "attackedId", String.class,
                "WinnerId", String.class,
                "bet", Integer.class
        ));
    }
}
