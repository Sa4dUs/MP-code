package server;

import org.junit.BeforeClass;
import org.junit.Test;
import server.characters.CharacterType;
import server.characters.PlayerCharacter;
import server.items.Weapon;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testChallengeService()
    {
        Player p1 = new Player();
        PlayerCharacter c1 = new PlayerCharacter();

        Weapon w1 = new Weapon();
        w1.setAttack(3);
        w1.setDefense(3);
        c1.setHealth(20);
        c1.setActiveWeaponL(w1);
        c1.setBreed(CharacterType.Hunter);
        p1.setCharacter(c1);


        Player p2 = new Player();
        PlayerCharacter c2 = new PlayerCharacter();

        Weapon w2 = new Weapon();
        w2.setAttack(3);
        w2.setDefense(3);
        c2.setHealth(20);
        c2.setActiveWeaponL(w2);
        c2.setBreed(CharacterType.Hunter);
        p2.setCharacter(c2);



        ChallengeRequest request = new ChallengeRequest(p1, p2, c1);
        request.sendToTarget();
        ChallengeRequest reqPlayer = p2.getPendingDuels().get(0);
        reqPlayer.accept();

        assertSame(p1.getResults().get(0), p2.getResults().get(0));

    }

}