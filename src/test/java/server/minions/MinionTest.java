package server.minions;

import org.junit.jupiter.api.Test;
import server.minions.Minion;

import static org.junit.jupiter.api.Assertions.*;

class MinionTest {

    @Test
    void testMinionInitializationWithNameAndHealth() {
        Minion minion = new Human();
        assertEquals("Undefined", minion.getName());
        assertEquals(1, minion.getHealth());
    }

    @Test
    void testMinionInitializationWithName() {
        Minion minion = new Human();
        minion.setName("Goblin");
        assertEquals("Goblin", minion.getName());
        assertEquals(1, minion.getHealth());
    }

    @Test
    void testMinionInitializationWithHealth() {
        Minion minion = new Ghoul();
        minion.setHealth(5);
        assertEquals("Undefined", minion.getName());
        assertEquals(3, minion.getHealth()); // Health capped at maxHealth
    }

    @Test
    void testMinionInitializationWithNameAndExcessiveHealth() {
        Minion minion = new Human();
        minion.setName("Orc");
        minion.setHealth(10);
        assertEquals("Orc", minion.getName());
        assertEquals(3, minion.getHealth()); // Health capped at maxHealth
    }

    @Test
    void testMinionDocumentGeneration() {
        Minion minion = new Human();
        minion.setName("Kobold");
        minion.setHealth(2);
        minion.setId("123");
        assertNotNull(minion.getDocument());
        assertEquals("123", minion.getDocument().getId());
        assertEquals("Kobold", minion.getDocument().getProperty("name"));
        assertEquals(2, minion.getDocument().getProperty("health"));
    }
}
