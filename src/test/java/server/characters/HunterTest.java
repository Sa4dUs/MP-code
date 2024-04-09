package server.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HunterTest {

    @Test
    public void testManaDecreasesWhenTakingDamage() {
        Hunter hunter = new Hunter(new PlayerCharacter(), "");
        int initialMana = hunter.getMana();

        hunter.receiveDamage();

        assertEquals(initialMana - 1, hunter.getMana());
    }
}