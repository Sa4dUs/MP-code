package server.characters;
import org.junit.jupiter.api.Test;
import server.*;
import server.items.Ability;
import server.items.Armor;
import server.items.Talent;
import server.items.Weapon;
import server.minions.Demon;
import server.minions.Minion;
import server.nosql.Document;
import server.nosql.Schemas.CharacterSchema;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void testGettersAndSetters() {
        Character character = new Character();
        character.setName("John");
        character.setHealth(100);
        character.setGold(50);
        character.setBreed(CharacterType.Hunter);

        assertEquals("John", character.getName());
        assertEquals(100, character.getHealth());
        assertEquals(50, character.getGold());
        assertEquals(CharacterType.Hunter, character.getBreed());
    }

    @Test
    void testAddWeaponToList() {
        Character character = new Character();
        Weapon weapon = new Weapon();
        character.addWeaponToList(weapon);
        assertEquals(1, character.getWeaponsList().size());
        assertTrue(character.getWeaponsList().contains(weapon));
    }

    @Test
    void testAddArmorToList() {
        Character character = new Character();
        Armor armor = new Armor();
        character.addArmorToList(armor);
        assertEquals(1, character.getArmorList().size());
        assertTrue(character.getArmorList().contains(armor));
    }

    @Test
    void testAddMinion() {
        Character character = new Character();
        Minion minion = new Demon();
        character.addMinion(minion);
        assertEquals(1, character.getMinionList().size());
        assertTrue(character.getMinionList().contains(minion));
    }

    @Test
    void testGetDocument() {
        Character character = new Character();
        character.setName("John");
        character.setHealth(100);
        character.setGold(50);
        character.setBreed(CharacterType.Hunter);

        Document document = character.getDocument();

        assertNotNull(document);
        assertEquals("John", document.getProperty("name"));
        assertEquals(100, document.getProperty("health"));
        assertEquals(50, document.getProperty("gold"));
        assertEquals(CharacterType.Hunter.ordinal(), document.getProperty("breed"));
    }

    @Test
    void testDocumentSpecialAbility() {
        Character character = new Character();
        Ability ability = new Talent();
        character.setSpecialAbility(ability);

        Document document = character.getDocument();

        assertNotNull(document);
        assertEquals(ability.getDocument().getId(), document.getProperty("specialAbility"));
    }

    @Test
    void testIdGeneration() {
        Character character = new Character();
        assertNull(character.getId()); // El ID debería ser nulo antes de generar el documento

        Document document = character.getDocument();
        assertNotNull(character.getId()); // El ID debería ser generado después de generar el documento
    }

    @Test
    void testCalculateMinionsKilledAfterDamage() {
        Character character = new Character();
        Demon demon1 = new Demon();
        demon1.setHealth(3);
        Demon demon2 = new Demon();
        demon2.setHealth(2);
        character.addMinion(demon1);
        character.addMinion(demon2);

        // Daño suficiente para matar a ambos esbirros
        int damageEnoughForAll = demon1.getHealth() + demon2.getHealth();
        assertEquals(2, character.calculateMinionsKilledAfterDamage(damageEnoughForAll));

        // Daño suficiente para matar solo a un esbirro
        int damageEnoughForOne = demon1.getHealth();
        assertEquals(1, character.calculateMinionsKilledAfterDamage(damageEnoughForOne));

        // Daño insuficiente para matar a ningún esbirro
        int damageNotEnough = 1;
        assertEquals(0, character.calculateMinionsKilledAfterDamage(damageNotEnough));
    }

    @Test
    void testGetMinionCount() {
        Character character = new Character();
        Demon demon1 = new Demon();
        Demon demon2 = new Demon();
        character.addMinion(demon1);
        character.addMinion(demon2);

        assertEquals(2, character.getMinionCount());
    }

    @Test
    void testRemoveGold() {
        Character character = new Character();
        character.setGold(100);
        character.removeGold(50);
        assertEquals(50, character.getGold());
    }

    @Test
    void testDocumentIdGeneration() {
        Character character = new Character();
        Document document = character.getDocument();
        assertNotNull(character.getId());
        assertEquals(character.getId(), document.getId());
    }

    @Test
    void testEmptyLists() {
        Character character = new Character();
        assertTrue(character.getWeaponsList().isEmpty());
        assertTrue(character.getArmorList().isEmpty());
        assertTrue(character.getMinionList().isEmpty());
        assertTrue(character.getDebilitiesList().isEmpty());
        assertTrue(character.getResistancesList().isEmpty());
    }

    @Test
    void testDocumentNullFields() {
        Character character = new Character();
        Document document = character.getDocument();

        assertEquals(document.getProperty("specialAbility"), "");
        assertNotNull(document.getProperty("id"));
    }

    @Test
    void testDebilitiesAndResistances() {
        Character character = new Character();
        Weakness weakness = new Weakness();
        Resistance resistance = new Resistance();

        character.getDebilitiesList().add(weakness);
        character.getResistancesList().add(resistance);

        assertTrue(character.getDebilitiesList().contains(weakness));
        assertTrue(character.getResistancesList().contains(resistance));
    }
}
