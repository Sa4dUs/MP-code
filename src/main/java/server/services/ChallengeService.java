package server.services;

import jdk.javadoc.doclet.Reporter;
import lib.ResponseBody;
import server.*;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.ChallengeRequestSchema;
import server.nosql.Schemas.ChallengeResultSchema;

import java.lang.reflect.Array;
import java.util.*;

public class ChallengeService implements Service {

    public ResponseBody createChallenge(ChallengeRequest challenge)
    {
        Document doc = new Document(new ChallengeRequestSchema());
        doc.setProperty("bet", challenge.getBet());
        doc.setProperty("attackerId", challenge.getAttackingPlayer().getNick());
        doc.setProperty("attackedId", challenge.getAttackedPlayer().getNick());
        Database.insertOne(Collection.CHALLENGE_OPERATORS, doc);
        challenge.setId(doc.getId());

        return new ResponseBody(true);
    }

    public ResponseBody getChallenge(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);
        Document doc = new Document();
        doc = Database.findOne(Collection.CHALLENGE, query);

        if(doc == null)
        {
            doc = Database.findOne(Collection.CHALLENGE_OPERATORS, query);
            if(doc == null)
                return new ResponseBody(false);
        }

        Player attackingPlayer = getPlayer((String) doc.getProperty("attackerId"));
        Player attackedPlayer = getPlayer((String) doc.getProperty("attackedId"));

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeRequest request = new ChallengeRequest(attackingPlayer, attackedPlayer);

        ResponseBody res = new ResponseBody(true);
        res.addField("data", doc);

        return res;
    }

    private Player getPlayer(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);

        Document doc = Database.findOne(Collection.USER, query);

        if(doc == null || (boolean) doc.getProperty("isOperator"))
            return null;

        PlayerCharacter character = getPlayerCharacter((String) doc.getProperty("playerCharacterId"));


        List<ChallengeRequest> requestList = new ArrayList<>();
        for(String s: (String[]) doc.getProperty("PendingDuelId"))
        {
            requestList.add((ChallengeRequest) getChallenge(s).getField("data"));
        }
        doc.setProperty("pendingDuels", requestList.toArray());

        return new Player(doc);
    }

    private Character getCharacter(String id)
    {
        return null;
    }

    private PlayerCharacter getPlayerCharacter(String id)
    {
        return null;
    }

    private Weapon getWeapon(String id)
    {
        return null;
    }

    private Armor getArmor(String id)
    {
        return null;
    }

    private Ability getAbility(String id)
    {
        return null;
    }

    private Characteristic getCharacteristic(String id)
    {
        return null;
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

    public ResponseBody acceptChallengeFromPlayer(ChallengeRequest challenge)
    {
        PlayerCharacter character = challenge.getAttackedPlayer().getCharacter();


        ChallengeResult result = new ChallengeResult(challenge, character);
        Query query = new Query();
        query.addFilter("id", challenge.getId());

        Database.deleteOne(Collection.CHALLENGE, query);

        Document resDoc = createResultDocument(result);

        Database.insertOne(Collection.CHALLENGE, resDoc);

        result.setId(resDoc.getId());

        addIdToPlayer(result.getId(), result.getAttackingPlayer().getNick(), "duelResultID");
        addIdToPlayer(result.getId(), result.getAttackedPlayer().getNick(), "duelResultID");

        removeIdFromPlayer(challenge.getId(), result.getAttackedPlayer().getNick(), "pendingDuelId");

        ResponseBody responseBody = new ResponseBody(true);
        responseBody.addField("result", result);

        return new ResponseBody(true);
    }

    public ResponseBody acceptChallengeFromOperator(ChallengeRequest challenge, String nick)
    {
        Query query = new Query();
        query.addFilter("id", challenge.getId());
        Database.deleteOne(Collection.CHALLENGE_OPERATORS, query);

        return addIdToPlayer(challenge.getId(), nick, "pendingDuelId");
    }

    public ResponseBody denyChallengeFromOperator(ChallengeRequest challenge)
    {
        ChallengeResult result = new ChallengeResult();

        Query query = new Query();
        query.addFilter("id", challenge.getId());
        Database.deleteOne(Collection.CHALLENGE_OPERATORS, query);

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
