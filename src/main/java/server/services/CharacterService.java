package server.services;

import lib.ResponseBody;
import server.Database;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.CharacterSchema;

public class CharacterService implements Service {
    public ResponseBody createCharacter(Character character)
    {
        Document doc = new Document(new CharacterSchema());
        characterToDocument(character, doc);

        Database.insertOne(Collection.DEFAULT_CHARACTER, doc);
        ResponseBody responseBody = new ResponseBody(true);
        responseBody.addField("id", doc.getProperty("id"));
        return responseBody;
    }


    public ResponseBody updateCharacter(String id, Character character)
    {
        Document newDoc = new Document(new CharacterSchema());
        characterToDocument(character, newDoc);

        Query query = new Query();
        query.addFilter("id", id);

        Database.updateOne(Collection.DEFAULT_CHARACTER, newDoc, query);

        return new ResponseBody(true);
    }

    public ResponseBody deleteCharacter(String id)
    {
        Query query = new Query();
        query.addFilter("id", id);

        Database.deleteOne(Collection.DEFAULT_CHARACTER, query);

        return new ResponseBody(true);
    }

    private void characterToDocument(Character character, Document doc)
    {
        doc.setProperty("id", character.getId());
        doc.setProperty("name", character.getName());
        doc.setProperty("breed", character.getBreed().ordinal());
        doc.setProperty("hp", character.getHealth());
        doc.setProperty("gold", character.getGold());
        doc.setProperty("weaponId", arrayToStringArray(character.getWeaponsList().toArray()));
        doc.setProperty("armorId", arrayToStringArray(character.getArmorList().toArray()));
        doc.setProperty("abilityId(N)", arrayToStringArray(character.getAbilityList().toArray()));
        doc.setProperty("abilityId(S)", arrayToStringArray(character.getSpecialAbilityList().toArray()));
        doc.setProperty("characteristicId(D)", arrayToStringArray(character.getDebilitiesList().toArray()));
        doc.setProperty("characteristicId(S)", arrayToStringArray(character.getResistancesList().toArray()));
        doc.setProperty("minionId", arrayToStringArray(character.getMinionList().toArray()));

    }

    private void playerCharacterToDocument(PlayerCharacter character, Document doc)
    {
        characterToDocument(character, doc);

        doc.setProperty("weaponId(LWeapon)", character.getActiveWeaponL().toString());
        doc.setProperty("weaponId(RWeapon)", character.getActiveWeaponR().toString());
        doc.setProperty("armorId(Active)", character.getActiveArmor().toString());
        doc.setProperty("abilityId(ActiveN)", character.getActiveNormalAbility().toString());
        doc.setProperty("abilityId(ActiveS)", character.getActiveSpecialAbility().toString());
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
