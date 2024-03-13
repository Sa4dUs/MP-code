package server;

import org.junit.Test;
import server.characters.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChallengeRequestTest {

    @Test
    public void testDuelRequestAccept(){
        Player attacking = new Player();
        Player attacked = new Player();
        PlayerCharacter character = new PlayerCharacter();
        attacking.setCharacter(character);
        ChallengeRequest request = new ChallengeRequest(attacking, attacked, character);

        //Act
        request.accept();

        //Asserts
        assertFalse(attacked.getPendingDuels().contains(request));
    }

    @Test
    public void testDuelRequestDenyFromPlayer(){
        Player attacking = new Player();
        Player attacked = new Player();
        PlayerCharacter character = new PlayerCharacter();
        attacking.setCharacter(character);
        ChallengeRequest request = new ChallengeRequest(attacking, attacked, character);

        //Act
        request.denyFromPlayer();

        //Asserts
        assertFalse(attacked.getPendingDuels().contains(request));
    }

    @Test
    public void testDuelRequestSendToTarget(){
        Player attacking = new Player();
        Player attacked = new Player();
        PlayerCharacter character = new PlayerCharacter();
        attacking.setCharacter(character);
        ChallengeRequest request = new ChallengeRequest(attacking, attacked, character);

        //Act
        request.sendToTarget();

        //Asserts
        assertTrue(attacked.getPendingDuels().contains(request));
    }

    @Test
    public void testDuelRequestDenyFromOperator(){
        Player attacking = new Player();
        Player attacked = new Player();
        PlayerCharacter character = new PlayerCharacter();
        attacking.setCharacter(character);
        ChallengeRequest request = new ChallengeRequest(attacking, attacked, character);

        //Act
        request.denyFromOperator();

        //Asserts
        assertFalse(attacked.getPendingDuels().contains(request));
    }
}
