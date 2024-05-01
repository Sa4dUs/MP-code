package server.minions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemonTest {

    @Test
    void testDemonInitialization() {
        Demon demon = new Demon();
        assertEquals("Undefined", demon.getName());
        assertEquals(1, demon.getHealth());
        assertNull(demon.getPact());
        assertTrue(demon.getMinions().isEmpty());
    }

    @Test
    void testDemonInitializationWithName() {
        Demon demon = new Demon();
        demon.setName("Lucifer");
        assertEquals("Lucifer", demon.getName());
        assertEquals(1, demon.getHealth());
        assertNull(demon.getPact());
        assertTrue(demon.getMinions().isEmpty());
    }

    @Test
    void testDemonInitializationWithPact() {
        Demon demon = new Demon();
        demon.setPact("Dark Covenant");
        assertEquals("Undefined", demon.getName());
        assertEquals(1, demon.getHealth());
        assertEquals("Dark Covenant", demon.getPact());
        assertTrue(demon.getMinions().isEmpty());
    }

    @Test
    void testDemonInitializationWithMinions() {
        Demon demon = new Demon();
        Human human = new Human();
        human.setHealth(2); // Asignar valor de vida específico para el humano
        Ghoul ghoul = new Ghoul();
        ghoul.setHealth(3); // Asignar valor de vida específico para el ghoul
        demon.addMinion(human);
        demon.addMinion(ghoul);
        assertEquals("Undefined", demon.getName());
        assertEquals(6, demon.getHealth()); // La vida del Demon es 1 por defecto, sumando los otros minions llega a 6
        assertNull(demon.getPact());
        assertFalse(demon.getMinions().isEmpty());
        assertEquals(2, demon.getMinions().size());
        assertTrue(demon.getMinions().contains(human));
        assertTrue(demon.getMinions().contains(ghoul));
        assertEquals(2, human.getHealth()); // Verificar que el humano tiene el valor de vida asignado
        assertEquals(3, ghoul.getHealth()); // Verificar que el ghoul tiene el valor de vida asignado
    }


    @Test
    void testDemonDocumentGeneration() {
        Demon demon = new Demon();
        demon.setName("Baal");
        demon.setPact("Infernal Pact");
        Human human = new Human();
        Ghoul ghoul = new Ghoul();
        demon.addMinion(human);
        demon.addMinion(ghoul);
        demon.setId("101");
        assertNotNull(demon.getDocument());
        assertEquals("101", demon.getDocument().getId());
        assertEquals("Baal", demon.getDocument().getProperty("name"));
        assertEquals("Infernal Pact", demon.getDocument().getProperty("pact"));
        assertFalse(demon.getMinions().isEmpty());
        assertTrue(demon.getMinions().contains(human));
        assertTrue(demon.getMinions().contains(ghoul));
    }

    @Test
    void testCalculateMinionsKilledAfterDamage() {
        Demon demon = new Demon();
        Human human = new Human();
        Ghoul ghoul = new Ghoul();
        Demon subDemon = new Demon();
        subDemon.setHealth(2);
        demon.addMinion(human);
        demon.addMinion(ghoul);
        demon.addMinion(subDemon);
        assertEquals(3, demon.calculateMinionsKilledAfterDamage(5));
    }

    @Test
    void testGetMinionCount() {
        Demon demon = new Demon();
        Human human = new Human();
        Ghoul ghoul = new Ghoul();
        Demon subDemon = new Demon();
        demon.addMinion(human);
        demon.addMinion(ghoul);
        demon.addMinion(subDemon);
        assertEquals(4, demon.getMinionCount());
    }

    @Test
    void testContainsMinion() {
        Demon demon = new Demon();
        Human human = new Human();
        Ghoul ghoul = new Ghoul();
        Demon subDemon = new Demon();
        demon.addMinion(human);
        demon.addMinion(ghoul);
        demon.addMinion(subDemon);
        assertTrue(demon.containsMinion(subDemon));
        assertFalse(subDemon.containsMinion(demon));
    }

    @Test
    void testCanAddMinion() {
        Demon demon = new Demon();
        Human human = new Human();
        Ghoul ghoul = new Ghoul();
        Demon subDemon = new Demon();
        demon.addMinion(human);
        demon.addMinion(ghoul);
        assertFalse(demon.canAddMinion(human));
        assertTrue(demon.canAddMinion(subDemon));
    }
}
