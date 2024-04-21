package server.minions;

import org.junit.jupiter.api.Test;
import server.minions.Human;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    @Test
    void testHumanInitialization() {
        Human human = new Human();
        assertEquals("Undefined", human.getName());
        assertEquals(1, human.getHealth());
        assertEquals(0, human.getLoyalty());
    }

    @Test
    void testHumanInitializationWithName() {
        Human human = new Human();
        human.setName("John");
        assertEquals("John", human.getName());
        assertEquals(1, human.getHealth());
        assertEquals(0, human.getLoyalty());
    }

    @Test
    void testHumanInitializationWithHealth() {
        Human human = new Human();
        human.setHealth(2);
        assertEquals("Undefined", human.getName());
        assertEquals(2, human.getHealth());
        assertEquals(0, human.getLoyalty());
    }

    @Test
    void testHumanInitializationWithLoyalty() {
        Human human = new Human();
        human.setLoyalty(2);
        assertEquals("Undefined", human.getName());
        assertEquals(1, human.getHealth());
        assertEquals(2, human.getLoyalty());
    }

    @Test
    void testHumanDocumentGeneration() {
        Human human = new Human();
        human.setName("Alice");
        human.setHealth(2);
        human.setLoyalty(3);
        human.setId("456");
        assertNotNull(human.getDocument());
        assertEquals("456", human.getDocument().getId());
        assertEquals("Alice", human.getDocument().getProperty("name"));
        assertEquals(2, human.getDocument().getProperty("health"));
        assertEquals(3, human.getDocument().getProperty("loyalty"));
    }
}
