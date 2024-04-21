package server.minions;

import org.junit.jupiter.api.Test;
import server.minions.Ghoul;

import static org.junit.jupiter.api.Assertions.*;

class GhoulTest {

    @Test
    void testGhoulInitialization() {
        Ghoul ghoul = new Ghoul();
        assertEquals("Undefined", ghoul.getName());
        assertEquals(1, ghoul.getHealth());
        assertEquals(0, ghoul.getDependence());
    }

    @Test
    void testGhoulInitializationWithName() {
        Ghoul ghoul = new Ghoul();
        ghoul.setName("Zombie");
        assertEquals("Zombie", ghoul.getName());
        assertEquals(1, ghoul.getHealth());
        assertEquals(0, ghoul.getDependence());
    }

    @Test
    void testGhoulInitializationWithHealth() {
        Ghoul ghoul = new Ghoul();
        ghoul.setHealth(2);
        assertEquals("Undefined", ghoul.getName());
        assertEquals(2, ghoul.getHealth());
        assertEquals(0, ghoul.getDependence());
    }

    @Test
    void testGhoulInitializationWithDependence() {
        Ghoul ghoul = new Ghoul();
        ghoul.setDependence(3);
        assertEquals("Undefined", ghoul.getName());
        assertEquals(1, ghoul.getHealth());
        assertEquals(3, ghoul.getDependence());
    }

    @Test
    void testGhoulDocumentGeneration() {
        Ghoul ghoul = new Ghoul();
        ghoul.setName("Wraith");
        ghoul.setHealth(2);
        ghoul.setDependence(3);
        ghoul.setId("789");
        assertNotNull(ghoul.getDocument());
        assertEquals("789", ghoul.getDocument().getId());
        assertEquals("Wraith", ghoul.getDocument().getProperty("name"));
        assertEquals(2, ghoul.getDocument().getProperty("health"));
        assertEquals(3, ghoul.getDocument().getProperty("dependence"));
    }
}
