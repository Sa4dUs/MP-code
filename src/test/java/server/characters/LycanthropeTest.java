package server.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LycanthropeTest {

    @Test
    public void testReceiveDamageIncreasesMana() {
        Lycanthrope lycanthrope = new Lycanthrope(new PlayerCharacter());

        int initialMana = lycanthrope.getMana();

        lycanthrope.receiveDamage();

        assertEquals(initialMana + 1, lycanthrope.getMana());
    }
}