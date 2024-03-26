package server.services;

import jdk.javadoc.doclet.Reporter;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.Database;
import server.Player;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.ChallengeRequestSchema;

import java.util.Map;

public class ChallengeService implements Service {

    public ResponseBody createChallenge(ChallengeRequest challenge)
    {
        Document doc = new Document(new ChallengeRequestSchema());
        doc.setProperty("bet", challenge.getBet());
        doc.setProperty("attackerId", challenge.getAttackingPlayer());
        doc.setProperty("attackedId", challenge.getAttackedPlayer());
        Database.insertOne(Collection.CHALLENGE, doc);
        return new ResponseBody(true);
    }

    public Document getChallenge(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);
        return Database.findOne(Collection.CHALLENGE, query);
    }

    public ResponseBody acceptChallenge(ChallengeRequest challenge)
    {
        return new ResponseBody(true);
    }

    public ResponseBody sendChallengeToPlayer(ChallengeRequest challenge, String nick)
    {
        Query query = new Query();
        query.addFilter("nick", nick);
        Document playerDoc = Database.findOne(Collection.USER, query);
        if (playerDoc == null)
            return new ResponseBody(false);

        String[] challenges;
        try
        {
            challenges = (String[]) playerDoc.getProperty("pendingDuelId");;
        } catch (Exception e)
        {
            return new ResponseBody(false);
        }

        if (challenges == null)
            return new ResponseBody(false);

        String[] newChallenges = new String[challenges.length + 1];

        System.arraycopy(challenges, 0, newChallenges, 0, challenges.length);
        newChallenges[challenges.length] = challenge.getId();
        challenges = newChallenges;

        playerDoc.setProperty("pendingDuelId", challenges);

        Database.updateOne(Collection.USER, playerDoc, query);

        return new ResponseBody(true);

    }

    public ResponseBody denyChallenge(ChallengeRequest challenge)
    {
        return new ResponseBody(true);
    }
}
