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
        ResponseBody response = new ResponseBody();

        Database.insertOne(Collection.CHALLENGE_OPERATORS, challenge.getDocument());

        response.setOk(true);
        return response;
    }

    public ResponseBody getChallenge(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);
        Document doc = Database.findOne(ChallengeRequest.class.getName(), query);

        if(doc == null)
        {
            doc = Database.findOne(Collection.CHALLENGE_OPERATORS, query);
            if(doc == null)
                return new ResponseBody(false);
        }

        ChallengeRequest request = (ChallengeRequest) doc.deJSONDocument(ChallengeRequest.class);

        if(request.getAttackedId() == null || request.getAttackerId() == null)
            return new ResponseBody(false);

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

        resultDoc.saveToDatabase(ChallengeResult.class);

        addIdToPlayer(resultDoc.getId(), attackingPlayer.getId(), "results");
        addIdToPlayer(resultDoc.getId(), attackedPlayer.getId(), "results");

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(ChallengeRequest.class.getName(), query);

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
        Database.insertOne(ChallengeRequest.class.getName(), doc);

        return addIdToPlayer(challenge.getAttackedId(), doc.getId(), "pendingDuels");
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

        Database.insertOne(ChallengeResult.class.getName(), resultDoc);

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

        Database.insertOne(ChallengeResult.class.getName(), resultDoc);

        addIdToPlayer(resultDoc.getId(), attackingPlayer.getId(), "duelResultID");
        addIdToPlayer(resultDoc.getId(), attackedPlayer.getId(), "duelResultID");

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(ChallengeRequest.class.getName(), query);

        return new ResponseBody(true);
    }

    public ResponseBody addIdToPlayer(String id, String nick, String property)
    {
        Query query = new Query();
        query.addFilter("id", nick);
        Document playerDoc = Database.findOne(Player.class.getName(), query);
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

        Database.updateOne(Player.class.getName(), playerDoc, query);

        return new ResponseBody(true);
    }

    public ResponseBody removeIdFromPlayer(String id, String nick, String property)
    {
        Query query = new Query();
        query.addFilter("id", nick);
        Document playerDoc = Database.findOne(Player.class.getName(), query);

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

        Database.updateOne(Player.class.getName(), playerDoc, query);

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

    public ResponseBody getOperatorChallenges() {
        ResponseBody response = new ResponseBody();

        List<ChallengeRequest> challengeList = Database.findMany(Collection.CHALLENGE_OPERATORS, new Query()).stream().map(e -> (ChallengeRequest) e.deJSONDocument(ChallengeRequest.class)).toList();

        response.addField("list", challengeList);
        response.setOk(true);
        return response;
    }
}
