package server.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import server.Player;
import server.characters.Character;
import server.characters.CharacterType;
import server.characters.PlayerCharacter;
import server.nosql.Document;

import static org.junit.jupiter.api.Assertions.*;

class CharacterServiceTest {

    @Test
    public void generateCharacterTest()
    {
        Character character = new Character();
        character.setBreed(CharacterType.Hunter);
        character.setName("Vayne");
        character.setHealth(1);
        character.setGold(500);

        CharacterService service = new CharacterService();
        service.createCharacter(character);

        Character character1 = (Character) Document.getDocument(character.getId(), Character.class).deJSONDocument(Character.class);
        Assert.assertEquals(character1.getName(), character.getName());
    }

    @Test
    public void generatePlayerCharacterTest()
    {
        PlayerCharacter character = new PlayerCharacter();
        character.setBreed(CharacterType.Vampire);
        character.setName("Shion");
        character.setHealth(3);
        character.setGold(500);

        CharacterService service = new CharacterService();
        service.createCharacter(character);

        PlayerCharacter character1 = (PlayerCharacter) Document.getDocument(character.getId(), PlayerCharacter.class).deJSONDocument(PlayerCharacter.class);
        Assert.assertEquals(character1.getName(), character.getName());
    }

}