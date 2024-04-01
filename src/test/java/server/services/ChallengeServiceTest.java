package server.services;

import lib.RandomGenerator;
import org.junit.jupiter.api.Test;
import server.ChallengeRequest;
import server.ChallengeResult;
import server.Database;
import server.Player;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    @Test
    public void acceptFromOperatorTest()
    {
        Query query = new Query();
        query.addFilter("id", "936bc5e1-07a4-4adc-9440-43fd88e07b67");

        ChallengeRequest request =(ChallengeRequest) Database.findOne(Collection.CHALLENGE_OPERATORS, query).deJSONDocument(ChallengeRequest.class);
        ChallengeService service = new ChallengeService();
        service.acceptChallengeFromOperator(request);
    }

    @Test
    public void createResultTest()
    {
        Player p1 = (Player) Document.getDocument("L60YY", Player.class).deJSONDocument(Player.class);
        Player p2 = (Player) Document.getDocument("F90WW", Player.class).deJSONDocument(Player.class);
        Player p3 = (Player) Document.getDocument("W46AA", Player.class).deJSONDocument(Player.class);

        //ChallengeResult result = new ChallengeResult(p1, p2, 50);
        //ChallengeResult result1 = new ChallengeResult(p2, p1, 69);
        //ChallengeResult result2 = new ChallengeResult(p3, p2, 100);
        //ChallengeResult result3 = new ChallengeResult(p3, p1, 500);

        ChallengeRequest request = new ChallengeRequest(p1.getId(), p2.getId(), 100);
        ChallengeRequest request1 = new ChallengeRequest(p1.getId(), p3.getId(), 230);
        ChallengeRequest request2 = new ChallengeRequest(p3.getId(), p2.getId(), 10);
        ChallengeRequest request3 = new ChallengeRequest(p3.getId(), p1.getId(), 50);
        ChallengeRequest request4 = new ChallengeRequest(p2.getId(), p3.getId(), 300);
        ChallengeRequest request5 = new ChallengeRequest(p1.getId(), p2.getId(), 60);
        ChallengeRequest request6 = new ChallengeRequest(p2.getId(), p1.getId(), 500);

        p1.addPending(request3);
        p2.addPending(request5);
        p2.addPending(request);
        p3.addPending(request1);

        p1.getDocument().saveToDatabase(Player.class);
        p2.getDocument().saveToDatabase(Player.class);
        p3.getDocument().saveToDatabase(Player.class);

        //result.getDocument().saveToDatabase(ChallengeResult.class);
        //result1.getDocument().saveToDatabase(ChallengeResult.class);
        //result2.getDocument().saveToDatabase(ChallengeResult.class);
        //result3.getDocument().saveToDatabase(ChallengeResult.class);

        request1.getDocument().saveToDatabase(ChallengeRequest.class);
        request3.getDocument().saveToDatabase(ChallengeRequest.class);
        request5.getDocument().saveToDatabase(ChallengeRequest.class);

        Database.insertOne(Collection.CHALLENGE_OPERATORS, request.getDocument());
        Database.insertOne(Collection.CHALLENGE_OPERATORS, request2.getDocument());
        Database.insertOne(Collection.CHALLENGE_OPERATORS, request4.getDocument());
        Database.insertOne(Collection.CHALLENGE_OPERATORS, request6.getDocument());


    }

}