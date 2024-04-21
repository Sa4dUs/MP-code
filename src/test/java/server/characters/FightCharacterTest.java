package server.characters;

import org.junit.jupiter.api.Test;
import server.items.Armor;
import server.items.Talent;
import server.items.Weapon;

import static org.junit.jupiter.api.Assertions.*;

class FightCharacterTest {

    @Test
    void testFightCharacterInitialization() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setName("Test Character");
        playerCharacter.setHealth(100);
        playerCharacter.setActiveWeaponL(new Weapon("Sword", 10, 0, false));
        playerCharacter.setActiveArmor(new Armor("Shield", 0, 10));
        playerCharacter.setActiveSpecialAbility(new Talent("Fireball", 15, 10, 2));

        FightCharacter fightCharacter = new Hunter(playerCharacter, "Test Player");

        assertEquals("Test Character", fightCharacter.getName());
        assertEquals(100, fightCharacter.getHealth());
        assertEquals(3, fightCharacter.getMana());
    }

    @Test
    void testReceiveDamageAndManaAfterDamage() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setName("Test Character");
        playerCharacter.setHealth(100);
        Talent talent = new Talent();
        talent.setName("Fireball");
        talent.setCost(2);
        talent.setAttack(3);
        playerCharacter.setActiveSpecialAbility(new Talent("Fireball", 3, 3, 3));

        FightCharacter fightCharacter = new Lycanthrope(playerCharacter, "Test Player");

        assertEquals(100, fightCharacter.getHealth());
        assertEquals(0, fightCharacter.getMana());

        fightCharacter.receiveDamage();

        assertEquals(99, fightCharacter.getHealth());
        assertEquals(1, fightCharacter.getMana());
    }

    @Test
    void testManaIncrementForLycanthropeAfterDamage() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setName("Test Character");
        playerCharacter.setHealth(100);
        playerCharacter.setActiveSpecialAbility(new Talent("Life Drain", 20, 10, 2));

        FightCharacter fightCharacter = new Lycanthrope(playerCharacter, "Test Player");

        assertEquals(100, fightCharacter.getHealth());
        assertEquals(0, fightCharacter.getMana());

        fightCharacter.receiveDamage();

        assertEquals(99, fightCharacter.getHealth());
        assertEquals(1, fightCharacter.getMana());
    }

    @Test
    void testTickMethodForHunter() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setName("Test Character");
        playerCharacter.setHealth(100);
        playerCharacter.setActiveSpecialAbility(new Talent("Snipe", 15, 10, 3));

        FightCharacter fightCharacter = new Hunter(playerCharacter, "Test Player");

        assertEquals(100, fightCharacter.getHealth());
        assertEquals(3, fightCharacter.getMana());

        fightCharacter.tick();

        assertEquals(3, fightCharacter.getMana()); // Mana no debería cambiar para Hunter después de llamar a tick
    }

    @Test
    void testLastTurnString() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setName("Test Character");
        playerCharacter.setHealth(100);
        playerCharacter.setActiveSpecialAbility(new Talent("Sonic Boom", 15, 10, 2));

        FightCharacter fightCharacter = new Lycanthrope(playerCharacter, "Test Player");

        String expectedTurn = "(Test Player) Test Character";
        assertEquals(expectedTurn, fightCharacter.getLastTurn());
    }
}
