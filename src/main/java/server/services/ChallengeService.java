package server.services;

import jdk.javadoc.doclet.Reporter;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.ChallengeResult;
import server.Database;
import server.Player;
import server.characters.PlayerCharacter;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.ChallengeRequestSchema;
import server.nosql.Schemas.ChallengeResultSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public ResponseBody getChallenge(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);
        Document doc = Database.findOne(Collection.CHALLENGE, query);

        if(doc == null)
            return new ResponseBody(false);

        ResponseBody res = new ResponseBody(true);
        res.addField("data", doc);

        return res;
    }

    private Document createResultDocument(ChallengeResult result)
    {
        Document resDoc = new Document(new ChallengeResultSchema());
        resDoc.setProperty("attackerPlayerId", result.getAttackingPlayer().getNick());
        resDoc.setProperty("attackedPlayerId", result.getAttackedPlayer().getNick());
        resDoc.setProperty("isWinnerAttacking", result.isWinnerAttacking());
        resDoc.setProperty("turns", result.getTurns());
        resDoc.setProperty("attackerMinionsLeft", result.getAttackerMinionsLeft());
        resDoc.setProperty("attackerMinionsLeft", result.getAttackerMinionsLeft());
        resDoc.setProperty("bet", result.getBet());

        return  resDoc;
    }

    public ResponseBody acceptChallengeFromPlayer(ChallengeRequest challenge, PlayerCharacter character)
    {
        ChallengeResult result = new ChallengeResult(challenge, character);
        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE, query);

        Document resDoc = createResultDocument(result);

        Database.insertOne(Collection.CHALLENGE, resDoc);

        addIdToPlayer(result.getId(), result.getAttackingPlayer().getNick(), "duelResultID");
        addIdToPlayer(result.getId(), result.getAttackedPlayer().getNick(), "duelResultID");

        removeIdFromPlayer(challenge.getId(), result.getAttackedPlayer().getNick(), "pendingDuelId");

        return new ResponseBody(true);
    }

    public ResponseBody acceptChallengeFromOperator(ChallengeRequest challenge, String nick)
    {
        return addIdToPlayer(challenge.getId(), nick, "pendingDuelId");
    }

    public ResponseBody denyChallengeFromOperator(ChallengeRequest challenge)
    {
        ChallengeResult result = new ChallengeResult();
        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE, query);

        Document resDoc = createResultDocument(result);

        Database.insertOne(Collection.CHALLENGE, resDoc);
        addIdToPlayer(result.getId(), challenge.getAttackingPlayer().getNick(), "duelResultID");

        return new ResponseBody(true);
    }

    public ResponseBody denyChallengeFromPlayer(ChallengeRequest challenge)
    {
        ChallengeResult result = new ChallengeResult(challenge);

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE, query);

        Document resDoc = createResultDocument(result);

        Database.insertOne(Collection.CHALLENGE, resDoc);

        addIdToPlayer(result.getId(), result.getAttackingPlayer().getNick(), "duelResultID");
        addIdToPlayer(result.getId(), result.getAttackedPlayer().getNick(), "duelResultID");

        removeIdFromPlayer(challenge.getId(), result.getAttackedPlayer().getNick(), "pendingDuelId");

        return new ResponseBody(true);
    }

    public ResponseBody addIdToPlayer(String id, String nick, String property)
    {
        Query query = new Query();
        query.addFilter("nick", nick);
        Document playerDoc = Database.findOne(Collection.USER, query);
        if (playerDoc == null)
            return new ResponseBody(false);

        String[] array;
        array = (String[]) playerDoc.getProperty(property);;

        if (array == null)
            return new ResponseBody(false);

        String[] newArray = new String[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = id;

        playerDoc.setProperty(property, newArray);

        Database.updateOne(Collection.USER, playerDoc, query);

        return new ResponseBody(true);
    }

    public ResponseBody removeIdFromPlayer(String id, String nick, String property)
    {
        Query query = new Query();
        query.addFilter("nick", nick);
        Document playerDoc = Database.findOne(Collection.USER, query);

        if (playerDoc == null)
            return new ResponseBody(false);

        String[] array;
        array = (String[]) playerDoc.getProperty(property);
        String[] newArray = new String[array.length - 1];

        int j = 0;
        for(int i = 0; i < array.length; i++)
        {
            if(array[i].equals(id))
            {
                j++;
                continue;
            }
            newArray[i - j] = array[i];

        }

        playerDoc.setProperty(property, newArray);

        Database.updateOne(Collection.USER, playerDoc, query);

        return new ResponseBody(true);
    }
}
