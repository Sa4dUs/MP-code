package server.services;

import org.junit.jupiter.api.Test;
import server.ChallengeRequest;
import server.Player;
import server.characters.PlayerCharacter;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    @Test
    public void createChallengeTest()
    {
        Player p1= new Player("Juan", "12415gs", "4321", false), p2 = new Player("Pepe", "y8g68", "1234", false);
        PlayerCharacter character = new PlayerCharacter();
        ChallengeRequest request = new ChallengeRequest(p1, p2, character);
        ChallengeService service = new ChallengeService();
        service.createChallenge(request);
    }

}