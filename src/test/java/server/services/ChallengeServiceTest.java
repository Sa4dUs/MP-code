package server.services;

import lib.ResponseBody;
import org.junit.jupiter.api.Test;
import server.Player;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    @Test
    public void testGeneral()
    {
        ChallengeService service = new ChallengeService();

        Player p1 = new Player("Pepe", "f3rfqf", "123456", false);
        ResponseBody responseBody = service.createObject(p1, Player.class);
    }

}