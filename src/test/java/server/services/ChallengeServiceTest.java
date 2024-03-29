package server.services;

import lib.ResponseBody;
import org.junit.jupiter.api.Test;
import server.Player;
import server.User;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    @Test
    public void testGeneral()
    {
        ChallengeService challengeService = new ChallengeService();
        AuthenticationService authenticationService = new AuthenticationService();

        User p1;
        ResponseBody responseBody = authenticationService.login("Pepe", "123456");
        p1 = (User) responseBody.getField("user");
    }

}