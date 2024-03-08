package server;

import org.junit.Test;
import server.characters.*;
import server.items.*;
import server.minions.*;
import server.services.*;
import server.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeResultTest {

    @Test
    public void testDuelResult()
    {
        Player p1 = new Player();
        Player p2 = new Player();

        PlayerCharacter attCharacter = new PlayerCharacter();
        PlayerCharacter defCharacter = new PlayerCharacter();

        attCharacter.setBreed(CharacterType.Hunter);
        defCharacter.setBreed(CharacterType.Hunter);

        attCharacter.setHealth(5);
        defCharacter.setHealth(5);


        Weapon wp1 = new Weapon();
        wp1.setAttack(1);

        Weapon wp2 = new Weapon();
        wp2.setAttack(0);

        attCharacter.setActiveWeaponL(wp1);
        defCharacter.setActiveWeaponL(wp2);

        ChallengeRequest request = new ChallengeRequest(p1, p2, attCharacter);
        ChallengeResult res = new ChallengeResult(request, defCharacter);

        assertTrue(res.isWinnerAttacking());
    }

    @Test
    public void Clase()
    {


        System.out.println(a);
    }
}