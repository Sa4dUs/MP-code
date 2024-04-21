package server;

import org.junit.jupiter.api.Test;
import server.characters.PlayerCharacter;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testGetCharacter() {
        Player player = new Player();
        assertNull(player.getCharacter());
    }

    @Test
    void testSetCharacter() {
        Player player = new Player();
        PlayerCharacter character = new PlayerCharacter();
        player.setCharacter(character);
        assertEquals(character, player.getCharacter());
    }

    @Test
    void testAddResult() {
        Player player = new Player();
        ChallengeResult result = new ChallengeResult();
        player.addResult(result);
        assertTrue(player.getResults().contains(result));
    }

    @Test
    void testDeletePendingChallenge() {
        Player player = new Player();
        ChallengeRequest pending = new ChallengeRequest();
        player.addPending(pending);
        player.deletePendingChallenge(pending);
        assertFalse(player.getPendingDuels().contains(pending));
    }

    @Test
    void testAddPending() {
        Player player = new Player();
        ChallengeRequest request = new ChallengeRequest();
        player.addPending(request);
        assertTrue(player.getPendingDuels().contains(request));
    }

    @Test
    void testIsBlocked() {
        Player player = new Player();
        assertFalse(player.isBlocked());

        player.setBlocked(true);
        assertTrue(player.isBlocked());

        player.setBlocked(false);
        assertFalse(player.isBlocked());
    }

    @Test
    void testChangeGold() {
        Player player = new Player();
        PlayerCharacter character = new PlayerCharacter();
        character.setGold(100);
        player.setCharacter(character);

        // Añadir oro
        player.changeGold(50);
        assertEquals(150, player.getCharacter().getGold());

        // Restar oro
        player.changeGold(-30);
        assertEquals(120, player.getCharacter().getGold());

        // Comprobar que el oro no puede ser negativo
        player.changeGold(-200);
        assertEquals(0, player.getCharacter().getGold());
    }
}

