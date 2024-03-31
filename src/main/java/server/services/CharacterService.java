package server.services;

import lib.ResponseBody;
import server.Database;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.CharacterSchema;

import java.util.ArrayList;
import java.util.List;

public class CharacterService implements Service {
    public ResponseBody createCharacter(Character character)
    {
        character.getDocument().saveToDatabase(character.getClass());
        return new ResponseBody(true);
    }


    public ResponseBody updateCharacter(Character character)
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

    private String[] arrayToStringArray(Object[] array)
    {
        String[] res = new String[array.length];

        for(int i = 0; i < array.length; i ++)
        {
            res[i] = array[i].toString();
        }

        return res;
    }
}
