package server.characters;

import org.junit.jupiter.api.Test;
import server.items.Ability;
import server.items.Armor;
import server.items.Talent;
import server.items.Weapon;
import server.nosql.Document;
import static org.junit.jupiter.api.Assertions.*;

class PlayerCharacterTest {

    @Test
    void testGettersAndSetters() {
        PlayerCharacter character = new PlayerCharacter();
        Weapon weaponL = new Weapon();
        Weapon weaponR = new Weapon();
        Armor armor = new Armor();
        Ability normalAbility = new Talent();
        Ability specialAbility = new Talent();

        character.setActiveWeaponL(weaponL);
        character.setActiveWeaponR(weaponR);
        character.setActiveArmor(armor);
        character.setActiveSpecialAbility(specialAbility);

        assertEquals(weaponL, character.getActiveWeaponL());
        assertEquals(weaponR, character.getActiveWeaponR());
        assertEquals(armor, character.getActiveArmor());
        assertEquals(specialAbility, character.getActiveSpecialAbility());
    }

    @Test
    void testGetDocument() {
        PlayerCharacter character = new PlayerCharacter();
        Document document = character.getDocument();

        assertNotNull(document);
        // Se espera que los campos estén inicialmente vacíos
        assertEquals("", document.getProperty("activeWeaponL"));
        assertEquals("", document.getProperty("activeWeaponR"));
        assertEquals("", document.getProperty("activeArmor"));
        assertEquals("", document.getProperty("activeSpecialAbility"));
    }
}
