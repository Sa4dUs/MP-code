package server.services;

import lib.ResponseBody;
import org.json.JSONArray;
import server.*;
import server.characters.PlayerCharacter;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.ChallengeRequestSchema;

import java.util.*;

public class ChallengeService implements Service {

    public ResponseBody createChallenge(ChallengeRequest challenge)
    {
        ResponseBody response = new ResponseBody();
        Player attacker = (Player) Document.getDocument(challenge.getAttackerId(), Player.class).deJSONDocument(Player.class);
        Player attacked = (Player) Document.getDocument(challenge.getAttackedId(), Player.class).deJSONDocument(Player.class);

        if(attacked.isBlocked() || attacker.isBlocked() || attacker.getCharacter() == null || attacked.getCharacter() == null || attacker.getCharacter().getGold() < challenge.getBet() || attacked.getCharacter().getGold() < challenge.getBet())
            return new ResponseBody(false);

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

        if(attackingPlayer == null || attackedPlayer == null || attackedPlayer.getCharacter() == null || attackingPlayer.getCharacter() == null && (attackedPlayer.getCharacter().getActiveWeaponL() == null && attackedPlayer.getCharacter().getActiveWeaponR() == null) || (attackingPlayer.getCharacter().getActiveWeaponL() == null && attackingPlayer.getCharacter().getActiveWeaponR() == null))
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet);
        Document resultDoc = challengeResult.getDocument();

        resultDoc.saveToDatabase(ChallengeResult.class);

        if(challengeResult.isWinnerAttacking())
        {
            attackingPlayer.getCharacter().removeGold(-challengeResult.getBet());
            attackingPlayer.getCharacter().getDocument().saveToDatabase(PlayerCharacter.class);
            attackedPlayer.getCharacter().removeGold(challengeResult.getBet());
            attackedPlayer.getCharacter().getDocument().saveToDatabase(PlayerCharacter.class);
            attackedPlayer.setBlocked(true);
        }
        else
        {
            attackingPlayer.getCharacter().removeGold(challengeResult.getBet());
            attackedPlayer.getCharacter().removeGold(-challengeResult.getBet());
            attackingPlayer.setBlocked(true);
        }

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


        if(!addIdToPlayer(doc.getId(), challenge.getAttackedId(), "pendingDuels").ok)
            return new ResponseBody(false);

        Database.deleteOne(Collection.CHALLENGE_OPERATORS, query);
        Database.insertOne(ChallengeRequest.class.getName(), doc);

        return new ResponseBody(true);

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

        addIdToPlayer(resultDoc.getId(), attackingPlayer.getId(), "results");

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
        ChallengeResult result =(ChallengeResult) resultDoc.deJSONDocument(ChallengeResult.class);

        Database.insertOne(ChallengeResult.class.getName(), resultDoc);

        attackingPlayer.addResult(result);
        attackedPlayer.addResult(result);

        attackingPlayer.deletePendingChallenge(challenge);
        attackedPlayer.deletePendingChallenge(challenge);

        bet = result.isWinnerAttacking() ? bet: -result.getBet();

        attackingPlayer.changeGold((int) Math.round(bet*0.1));
        attackedPlayer.changeGold((int) -Math.round(bet*0.1));

        attackingPlayer.getDocument().saveToDatabase(Player.class);
        attackedPlayer.getDocument().saveToDatabase(Player.class);

        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(ChallengeRequest.class.getName(), query);

        ResponseBody responseBody = new ResponseBody(true);
        responseBody.addField("data", challengeResult);

        return responseBody;
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

        JSONArray JSONArray = new JSONArray(Arrays.asList(newArray));

        playerDoc.setProperty(property, JSONArray);

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

        if (newArray.length == 0)
            playerDoc.setProperty(property, null);
        else
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

        response.addField("data", challengeList);
        response.setOk(true);
        return response;
    }
}
