package server;

import org.junit.jupiter.api.Test;
import server.characters.Character;
import server.characters.FightCharacter;
import server.characters.PlayerCharacter;
import server.items.Weapon;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeResultTest {

    @Test
    void testInitialization() {
        Weapon w1 = new Weapon("W1", 3, 0,false);
        Character character = new Character();
        character.setHealth(3);
        PlayerCharacter pc = new PlayerCharacter(character);
        pc.setActiveWeaponR(w1);

        Player attacker = new Player("Attacker", "1234");
        Player attacked = new Player("Attacked", "1234");

        attacker.setCharacter(pc);
        attacked.setCharacter(pc);

        ChallengeResult result = new ChallengeResult(attacker, attacked, 10);
        result.getId();
        assertNotNull(result.getId());
        assertEquals(10, result.getBet());
        assertEquals(attacker.getId(), result.getAttackerId());
        assertEquals(attacked.getId(), result.getAttackedPlayerId());
        assertTrue(result.getTurns() > 0);
        assertTrue(result.getDate().matches("\\d{2}/\\d{2}/\\d{4}, \\d{2}:\\d{2}"));
    }

    @Test
    void testCombatHistory() {
        Weapon w1 = new Weapon("W1", 3, 0,false);
        Character character = new Character();
        character.setHealth(3);
        PlayerCharacter pc = new PlayerCharacter(character);
        pc.setActiveWeaponR(w1);

        Player attacker = new Player("Attacker", "1234");
        Player attacked = new Player("Attacked", "1234");

        attacker.setCharacter(pc);
        attacked.setCharacter(pc);

        ChallengeResult result = new ChallengeResult(attacker, attacked, 10);

        List<String> history = result.getHistory();
        assertFalse(history.isEmpty());
        assertEquals(1, history.size() % 3);
    }

    @Test
    void testFinalAttributes() {
        Weapon w1 = new Weapon("W1", 3, 0,false);
        Character character = new Character();
        character.setHealth(3);
        PlayerCharacter pc = new PlayerCharacter(character);
        pc.setActiveWeaponR(w1);

        Player attacker = new Player("Attacker", "1234");
        Player attacked = new Player("Attacked", "1234");

        attacker.setCharacter(pc);
        attacked.setCharacter(pc);

        ChallengeResult result = new ChallengeResult(attacker, attacked, 10);

        assertEquals(0, result.getAttackerMinionsLeft() + result.getAttackedMinionsLeft()); // All minions should be dead
    }
}
