package server.services;

import org.junit.jupiter.api.Test;
import server.ChallengeRequest;
import server.ChallengeResult;
import server.Player;
import server.nosql.Document;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    @Test
    public void createResultTest()
    {
        Player p1 = (Player) Document.getDocument("L60YY", Player.class).deJSONDocument(Player.class);
        Player p2 = (Player) Document.getDocument("L28SS", Player.class).deJSONDocument(Player.class);
        Player p3 = (Player) Document.getDocument("C15PJ", Player.class).deJSONDocument(Player.class);
        ChallengeResult result = new ChallengeResult(p1, p2, 50);
        ChallengeResult result1 = new ChallengeResult(p2, p1, 69);
        ChallengeResult result2 = new ChallengeResult(p3, p2, 100);
        ChallengeResult result3 = new ChallengeResult(p3, p1, 500);

        result.getDocument().saveToDatabase(ChallengeResult.class);
        result1.getDocument().saveToDatabase(ChallengeResult.class);
        result2.getDocument().saveToDatabase(ChallengeResult.class);
        result3.getDocument().saveToDatabase(ChallengeResult.class);


    }

}