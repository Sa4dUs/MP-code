import org.junit.Test;
import server.ChallengeRequest;
import server.ChallengeResult;
import server.Player;
import server.characters.CharacterType;
import server.characters.Hunter;
import server.characters.PlayerCharacter;
import server.items.Weapon;

import java.io.Console;

public class ResultTest {
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
        wp1.setAttack(2);

        Weapon wp2 = new Weapon();
        wp2.setAttack(1);

        attCharacter.setActiveWeaponL(wp1);
        defCharacter.setActiveWeaponL(wp2);

        ChallengeRequest request = new ChallengeRequest(p1, p2, attCharacter);
        ChallengeResult res = new ChallengeResult(request, defCharacter);

    }
}
