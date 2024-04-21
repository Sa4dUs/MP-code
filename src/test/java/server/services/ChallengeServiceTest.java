package server.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Player;
import server.characters.Character;
import server.characters.PlayerCharacter;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    private CharacterService characterService;
    private static Player player;
    private static PlayerCharacter character;
    @BeforeEach
    void setUp() {
        characterService = new CharacterService();

        character = new PlayerCharacter();
        character.setId("00000000-0000-0000-0000-000000000000");
        character.getDocument().saveToDatabase(character.getClass());

        player = new Player();
        player.setId("A12BC");
        player.setCharacter(character);
        player.getDocument().saveToDatabase(player.getClass());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCharacter() {
        Character character = new Character();
        assertTrue(characterService.createCharacter(character).ok);
    }

    @Test
    void createPlayerCharacter() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        assertTrue(characterService.createPlayerCharacter(playerCharacter).ok);
    }

    @Test
    void setCharacterOfPlayer() {
        assertTrue(characterService.setCharacterOfPlayer(player.getNick(), character).ok);
    }

    @Test
    void updateCharacter() {
        Character character = new Character(/* Initialize character data */);
        assertTrue(characterService.updateCharacter(character).ok);
    }

    @Test
    void updatePlayerCharacter() {
        assertTrue(characterService.updatePlayerCharacter(character).ok);
    }

    @Test
    void deleteCharacter() {
        assertTrue(characterService.deleteCharacter(character.getId(), Character.class).ok);
    }

    @Test
    void getDefaultCharacters() {
        assertNotNull(characterService.getDefaultCharacters().getField("characterList"));
    }

    @Test
    void getPlayerCharacters() {
        assertNotNull(characterService.getPlayerCharacters().getField("characterList"));
    }

    @Test
    void setIdToPlayer() {
        assertTrue(characterService.setIdToPlayer(character.getId(), player.getNick(), "character").ok);
    }

    /*
    void removeIdFromPlayer() {
        assertTrue(characterService.removeIdFromPlayer(character.getId(), player.getNick(), "character").ok);
    }
    */

    @Test
    void getCharacterFromPlayerNick() {
        assertNotNull(characterService.getCharacterFromPlayerNick(player.getNick()).getField("character"));
    }
}
