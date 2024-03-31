package server.services;

import lib.ResponseBody;
import server.*;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.ChallengeRequestSchema;

import java.util.*;

public class ChallengeService implements Service {

    public ResponseBody createChallenge(ChallengeRequest challenge)
    {
        Document doc = new Document(new ChallengeRequestSchema());
        doc.setProperty("bet", challenge.getBet());
        doc.setProperty("attackerId", challenge.getAttackerId());
        doc.setProperty("attackedId", challenge.getAttackedId());
        Database.insertOne(Collection.CHALLENGE_OPERATORS, doc);
        challenge.setId(doc.getId());

        return new ResponseBody(true);
    }

    public ResponseBody getChallenge(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);
        Document doc = Database.findOne(Collection.CHALLENGE, query);

        if(doc == null)
        {
            doc = Database.findOne(Collection.CHALLENGE_OPERATORS, query);
            if(doc == null)
                return new ResponseBody(false);
        }

        String attackingPlayer = (String) doc.getProperty("attackerId");
        String attackedPlayer = (String) doc.getProperty("attackedId");
        int bet = (int) doc.getProperty("bet");

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeRequest request = new ChallengeRequest(attackingPlayer, attackedPlayer, bet);

        ResponseBody res = new ResponseBody(true);
        res.addField("data", request);

        return res;
    }

    public ResponseBody acceptChallengeFromPlayer(ChallengeRequest challenge)
    {
        Player attackingPlayer = (Player) Document.getObjectFromDoc(challenge.getAttackerId(), Player.class);
        Player attackedPlayer = (Player) Document.getObjectFromDoc(challenge.getAttackedId(), Player.class);
        int bet = challenge.getBet();

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet);
        Document resultDoc = challengeResult.getDocument();

        Database.insertOne(Collection.CHALLENGE, resultDoc);

        addIdToPlayer(resultDoc.getId(), attackingPlayer.getId(), "duelResultID");
        addIdToPlayer(resultDoc.getId(), attackedPlayer.getId(), "duelResultID");

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE, query);

        ResponseBody res = new ResponseBody(true);
        res.addField("data", challengeResult);

        return res;
    }

    //Terminado
    public ResponseBody acceptChallengeFromOperator(ChallengeRequest challenge)
    {
        Query query = new Query();
        query.addFilter("id", challenge.getId());
        Document doc = Database.findOne(Collection.CHALLENGE_OPERATORS, query);

        if(doc == null)
            return  new ResponseBody(false);

        Database.deleteOne(Collection.CHALLENGE_OPERATORS, query);
        Database.insertOne(Collection.CHALLENGE, doc);

        return addIdToPlayer(challenge.getAttackedId(), doc.getId(), "pendingDuelId");
    }

    //Terminado
    public ResponseBody denyChallengeFromOperator(ChallengeRequest challenge)
    {
        Player attackingPlayer = (Player) Document.getObjectFromDoc(challenge.getAttackerId(), Player.class);
        Player attackedPlayer = (Player) Document.getObjectFromDoc(challenge.getAttackedId(), Player.class);
        int bet = challenge.getBet();

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet, true);
        Document resultDoc = challengeResult.getDocument();

        Database.insertOne(Collection.CHALLENGE, resultDoc);

        addIdToPlayer(resultDoc.getId(), attackingPlayer.getId(), "duelResultID");

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE_OPERATORS, query);

        return new ResponseBody(true);
    }

    //Terminado
    public ResponseBody denyChallengeFromPlayer(ChallengeRequest challenge)
    {
        Player attackingPlayer = (Player) Document.getObjectFromDoc(challenge.getAttackerId(), Player.class);
        Player attackedPlayer = (Player) Document.getObjectFromDoc(challenge.getAttackedId(), Player.class);
        int bet = challenge.getBet();

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet, false);
        Document resultDoc = challengeResult.getDocument();

        Database.insertOne(Collection.CHALLENGE, resultDoc);

        addIdToPlayer(resultDoc.getId(), attackingPlayer.getId(), "duelResultID");

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE, query);

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

    public ResponseBody getRanking() {
        ResponseBody response = new ResponseBody();

        List<Player> ranking = Database.findMany(Player.class.getName(), new Query()).stream()
                .map(e -> (Player) e.deJSONDocument(Player.class))
                .sorted(Comparator.comparing(u -> u.getCharacter() != null ? u.getCharacter().getGold() : 0, Comparator.reverseOrder()))
                .toList();

        response.addField("ranking", ranking);

        response.setOk(true);
        return response;
    }
}
