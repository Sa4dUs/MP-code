package server.items;

import org.junit.jupiter.api.Test;
import server.items.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemTests {

    // Tests for Armor class

    @Test
    void testArmorInitialization() {
        Armor armor = new Armor("Iron Armor", 5, 10);
        assertEquals("Iron Armor", armor.getName());
        assertEquals(5, armor.getAttack());
        assertEquals(10, armor.getDefense());
    }

    @Test
    void testArmorDocumentGeneration() {
        Armor armor = new Armor("Iron Armor", 5, 10);
        armor.setId("123");
        assertNotNull(armor.getDocument());
        assertEquals("123", armor.getDocument().getId());
        assertEquals("Iron Armor", armor.getDocument().getProperty("name"));
        assertEquals(5, armor.getDocument().getProperty("attack"));
        assertEquals(10, armor.getDocument().getProperty("defense"));
    }

    // Tests for Weapon class

    @Test
    void testWeaponInitialization() {
        Weapon weapon = new Weapon("Sword", 10, 5, true);
        assertEquals("Sword", weapon.getName());
        assertEquals(10, weapon.getAttack());
        assertEquals(5, weapon.getDefense());
        assertTrue(weapon.isTwoHanded());
    }

    @Test
    void testWeaponDocumentGeneration() {
        Weapon weapon = new Weapon("Sword", 10, 5, true);
        weapon.setId("456");
        assertNotNull(weapon.getDocument());
        assertEquals("456", weapon.getDocument().getId());
        assertEquals("Sword", weapon.getDocument().getProperty("name"));
        assertEquals(10, weapon.getDocument().getProperty("attack"));
        assertEquals(5, weapon.getDocument().getProperty("defense"));
        assertEquals("true", weapon.getDocument().getProperty("twoHanded"));
    }

    // Tests for Talent class

    @Test
    void testTalentInitialization() {
        Talent talent = new Talent("Double Strike", 8, 0, 3);
        assertEquals("Double Strike", talent.getName());
        assertEquals(8, talent.getAttack());
        assertEquals(0, talent.getDefense());
        assertEquals(3, talent.getCost());
    }

    @Test
    void testTalentDocumentGeneration() {
        Talent talent = new Talent("Double Strike", 8, 0, 3);
        talent.setId("789");
        assertNotNull(talent.getDocument());
        assertEquals("789", talent.getDocument().getId());
        assertEquals("Double Strike", talent.getDocument().getProperty("name"));
        assertEquals(8, talent.getDocument().getProperty("attack"));
        assertEquals(0, talent.getDocument().getProperty("defense"));
        assertEquals(3, talent.getDocument().getProperty("cost"));
    }
}
