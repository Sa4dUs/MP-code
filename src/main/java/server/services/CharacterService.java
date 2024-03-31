package server.services;

import lib.ResponseBody;
import server.Database;
import server.Player;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.CharacterSchema;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class CharacterService implements Service {
    public ResponseBody createCharacter(Character character)
    {
        character.getDocument().saveToDatabase(character.getClass());
        return new ResponseBody(true);
    }

    public ResponseBody createPlayerCharacter(PlayerCharacter character)
    {
        character.getDocument().saveToDatabase(character.getClass());
        return new ResponseBody(true);
    }

    public ResponseBody setCharacterOfPlayer(String nick, PlayerCharacter character)
    {
        Player player = (Player) Document.getDocument(nick, Player.class).deJSONDocument(Player.class);
        if(player.getCharacter() != null)
        {
            Query query = new Query();
            query.addFilter("id", player.getCharacter().getId());
            Database.updateOne(PlayerCharacter.class.getName(), character.getDocument(), query);
        }
        Document document = character.getDocument();
        return setIdToPlayer(document.getId(), nick, "character");
    }

    public ResponseBody updateCharacter(Character character)
    {
        character.getDocument().saveToDatabase(character.getClass());
        return new ResponseBody(true);
    }

    public ResponseBody updatePlayerCharacter(PlayerCharacter character)
    {
        character.getDocument().saveToDatabase(character.getClass());
        return new ResponseBody(true);
    }

    public ResponseBody deleteCharacter(String id, Class<?> clazz)
    {
        Query query = new Query();
        query.addFilter("id", id);

        Database.deleteOne(clazz.getName(), query);

        return new ResponseBody(true);
    }

    public ResponseBody getDefaultCharacters() {
        ResponseBody response = new ResponseBody();

        List<Document> docs = Database.findMany(Character.class.getName(), new Query());
        List<Character> characters = new ArrayList<>();

        docs.forEach(e -> characters.add((Character) e.deJSONDocument(Character.class)));

        response.addField("characterList", characters);

        response.setOk(true);
        return response;
    }

    public ResponseBody getPlayerCharacters() {
        ResponseBody response = new ResponseBody();

        List<Document> docs = Database.findMany(Character.class.getName(), new Query());
        List<Character> characters = new ArrayList<>();

        docs.forEach(e -> characters.add((Character) e.deJSONDocument(PlayerCharacter.class)));

        response.addField("characterList", characters);

        response.setOk(true);
        return response;
    }

    private String[] arrayToStringArray(Object[] array)
    {
        String[] res = new String[array.length];

        for(int i = 0; i < array.length; i ++)
        {
            res[i] = array[i].toString();
        }

        return res;
    }

    public ResponseBody setIdToPlayer(String id, String nick, String property)
    {
        Query query = new Query();
        query.addFilter("id", nick);
        Document playerDoc = Database.findOne(Player.class.getName(), query);
        if (playerDoc == null)
            return new ResponseBody(false);

        playerDoc.setProperty(property, id);

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

    public ResponseBody getCharacterFromPlayerNick(String nick) {
        ResponseBody response = new ResponseBody();
        Query query = new Query();
        query.addFilter("nick", nick);

        Player player = (Player) Database.findOne(Player.class.getName(), query).deJSONDocument(Player.class);

        if (player == null) {
            response.setOk(false);
            return response;
        }

        Character character = player.getCharacter();

        if (character == null) {
            response.setOk(false);
            return response;
        }

        response.addField("character", character);

        response.setOk(true);
        return response;
    }
}
