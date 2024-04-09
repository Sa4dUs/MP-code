package server.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import server.Player;
import server.characters.Character;
import server.characters.CharacterType;
import server.characters.PlayerCharacter;
import server.items.Ability;
import server.items.Blessing;
import server.items.Discipline;
import server.items.Talent;
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

        Ability ability = new Discipline();
        ability.setName("Voltereta");
        ability.setCost(2);
        ability.setAttack(3);
        ability.setDefense(2);

        ability.getDocument().saveToDatabase(Discipline.class);

        Ability ability1 = new Discipline();
        ability1.setName("Proyectiles de plata");
        ability1.setCost(2);
        ability1.setAttack(3);
        ability1.setDefense(0);
        character.setSpecialAbility(ability1);

        CharacterService service = new CharacterService();
        service.createCharacter(character);

        Character character1 = (Character) Document.getDocument(character.getId(), Character.class).deJSONDocument(Character.class);
        Assert.assertEquals(character1.getName(), character.getName());
    }

    @Test
    public void generatePlayerCharacterTest()
    {
        Character character = new Character();
        character.setBreed(CharacterType.Vampire);
        character.setName("Shion");
        character.setHealth(3);
        character.setGold(500);

        Ability ability = new Talent();
        ability.setName("Ostion");
        ability.setCost(1);
        ability.setAttack(3);
        ability.setDefense(0);

        ability.getDocument().saveToDatabase(Talent.class);

        Ability ability1 = new Blessing();
        ability1.setName("Patadoncio");
        ability1.setCost(1);
        ability1.setAttack(3);
        ability1.setDefense(0);
        character.setSpecialAbility(ability1);

        CharacterService service = new CharacterService();
        service.createCharacter(character);

        PlayerCharacter character1 = (PlayerCharacter) Document.getDocument(character.getId(), Character.class).deJSONDocument(Character.class);
        Assert.assertEquals(character1.getName(), character.getName());
    }

}