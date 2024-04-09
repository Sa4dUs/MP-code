package server.nosql.Schemas;

import server.nosql.Schema;

import java.util.Map;

public class ChallengeResultSchema extends Schema {
        public ChallengeResultSchema(){
        super(Map.of(
                "attackerId", String.class,
                "attackedId", String.class,
                "winnerAttacking", Boolean.class,
                "turns", Integer.class,
                "attackerMinionsLeft", Integer.class,
                "attackedMinionsLeft", Integer.class,
                "bet", Integer.class,
                "history", String[].class
        ));
    }
}
