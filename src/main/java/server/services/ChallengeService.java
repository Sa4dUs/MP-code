package server.services;

import lib.ResponseBody;
import server.*;
import server.characters.PlayerCharacter;
import server.items.Stats;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.ChallengeRequestSchema;
import server.nosql.Schemas.ChallengeResultSchema;

import java.lang.reflect.Field;
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
        Document doc = new Document();
        doc = Database.findOne(Collection.CHALLENGE, query);

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
        Player player = new Player(doc);
        player.setCharacter(character);
        return player;
    }

    //private Character getCharacter(String id)
    //{
    //    return null;
    //}

    private PlayerCharacter getPlayerCharacter(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);
        Document doc = Database.findOne(Collection.CHARACTER, query);
        if(doc == null)
            return null;

        deJSONDocument(doc, PlayerCharacter.class);

        return new PlayerCharacter(doc);
    }

    private void deJSONDocument(Document document, Class<?> clazz)
    {
        for(Field field: clazz.getDeclaredFields())
        {
            if(field.getType().isPrimitive())
                continue;

            if(field.getType().isArray())
                document.setProperty(field.getName(), getObjectArrayFromDoc((String[]) document.getProperty(field.getName()), field.getClass()));
            else
                document.setProperty(field.getName(), getObjectFromDoc((String) document.getProperty(field.getName()), field.getClass()));
        }
    }


    private Object[] getObjectArrayFromDoc(String[] ids, Class<?> clazz){
        List<Object> list = new ArrayList<>();

        for (String id: ids)
        {
            Query query = new Query();
            query.addFilter("id", id);
            Document doc = Database.findOne(Collection.ITEMS, query);
            deJSONDocument(doc, clazz);
            try
            {
                list.add(clazz.getDeclaredConstructor(Document.class).newInstance(doc));
            }
            catch (Exception e)
            {
                continue;
            }
        }
        return list.toArray(new Object[0]);
    }

    private Stats getObjectFromDoc(String id, Class<?> clazz) {
        Query query = new Query();
        query.addFilter("id", id);
        Document doc = Database.findOne(Collection.ITEMS, query);
        deJSONDocument(doc, clazz);
        try {
            return (Stats) clazz.getDeclaredConstructor(Document.class).newInstance(doc);
        } catch (Exception e) {
            return null;
        }
    }



    private Document createResultDocument(ChallengeResult result)
    {
        Document resDoc = new Document(new ChallengeResultSchema());
        resDoc.setProperty("attackerPlayerId", result.getAttackerPlayerId());
        resDoc.setProperty("attackedPlayerId", result.getAttackedPlayerId());
        resDoc.setProperty("isWinnerAttacking", result.isWinnerAttacking());
        resDoc.setProperty("turns", result.getTurns());
        resDoc.setProperty("attackerMinionsLeft", result.getAttackerMinionsLeft());
        resDoc.setProperty("attackerMinionsLeft", result.getAttackerMinionsLeft());
        resDoc.setProperty("bet", result.getBet());

        return  resDoc;
    }

    //Terminado
    public ResponseBody acceptChallengeFromPlayer(ChallengeRequest challenge)
    {
        Player attackingPlayer = getPlayer(challenge.getAttackerId());
        Player attackedPlayer = getPlayer(challenge.getAttackedId());
        int bet = challenge.getBet();

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet);
        Document resultDoc = createResultDocument(challengeResult);

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
        Player attackingPlayer = getPlayer(challenge.getAttackerId());
        Player attackedPlayer = getPlayer(challenge.getAttackedId());
        int bet = challenge.getBet();

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet, true);
        Document resultDoc = createResultDocument(challengeResult);

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
        Player attackingPlayer = getPlayer(challenge.getAttackerId());
        Player attackedPlayer = getPlayer(challenge.getAttackedId());
        int bet = challenge.getBet();

        if(attackingPlayer == null || attackedPlayer == null)
            return new ResponseBody(false);

        ChallengeResult challengeResult = new ChallengeResult(attackingPlayer, attackedPlayer, bet, false);
        Document resultDoc = createResultDocument(challengeResult);

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
}
