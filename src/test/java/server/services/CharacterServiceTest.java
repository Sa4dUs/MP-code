package server.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import lib.ResponseBody;

import static org.junit.jupiter.api.Assertions.*;

class CharacterServiceTest {
    public static CharacterService service;
    public static Player player;
    public Character character;
    public static PlayerCharacter playerCharacter;

    @BeforeAll
    static void setUp() {
        service = new CharacterService();

        playerCharacter = new PlayerCharacter();
        playerCharacter.setId("00000000-0000-0000-0000-000000000000");
        playerCharacter.getDocument().saveToDatabase(PlayerCharacter.class);

        player = new Player();
        player.setId("A12BC");
        player.setCharacter(playerCharacter);
        player.getDocument().saveToDatabase(player.getClass());
    }

    @Test
    public void generateCharacterTest()
    {
        character = new Character();
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
        playerCharacter = new PlayerCharacter();
        playerCharacter.setBreed(CharacterType.Vampire);
        playerCharacter.setName("Shion");
        playerCharacter.setHealth(3);
        playerCharacter.setGold(500);

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
        playerCharacter.setSpecialAbility(ability1);

        CharacterService service = new CharacterService();
        service.createCharacter(playerCharacter);
    }

    @Test
    void createCharacter() {
        Character character = new Character(/* initialize character data */);
        ResponseBody response = service.createCharacter(character);
        assertTrue(response.ok);
    }

    @Test
    void createPlayerCharacter() {
        PlayerCharacter character = new PlayerCharacter(/* initialize player character data */);
        ResponseBody response = service.createPlayerCharacter(character);
        assertTrue(response.ok);
    }

    @Test
    void setCharacterOfPlayer() {
        ResponseBody response = service.setCharacterOfPlayer(player.getNick(), playerCharacter);
        assertTrue(response.ok);
    }

    @Test
    void updateCharacter() {
        Character character = new Character(/* initialize character data */);
        ResponseBody response = service.updateCharacter(character);
        assertTrue(response.ok);
    }

    @Test
    void updatePlayerCharacter() {
        ResponseBody response = service.updatePlayerCharacter(playerCharacter);
        assertTrue(response.ok);
    }

    @Test
    void deleteCharacter() {
        String id = "00000000-0000-0000-0000-000000000000";
        Class<?> clazz = Character.class;
        ResponseBody response = service.deleteCharacter(id, clazz);
        assertTrue(response.ok);
    }

    @Test
    void getDefaultCharacters() {
        ResponseBody response = service.getDefaultCharacters();
        assertTrue(response.ok);
        assertNotNull(response.getField("characterList"));
    }

    @Test
    void getPlayerCharacters() {
        ResponseBody response = service.getPlayerCharacters();
        assertTrue(response.ok);
        assertNotNull(response.getField("characterList"));
    }

    @Test
    void setIdToPlayer() {
        ResponseBody response = service.setIdToPlayer(playerCharacter.getId(), player.getNick(), "character");
        assertTrue(response.ok);
    }

    @Test
    void getCharacterFromPlayerNick() {
        ResponseBody response = service.getCharacterFromPlayerNick(player.getNick());
        assertTrue(response.ok);
        assertNotNull(response.getField("character"));
    }
}