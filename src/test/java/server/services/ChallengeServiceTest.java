package server.services;

import lib.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ChallengeRequest;
import server.Player;
import server.characters.Character;
import server.characters.PlayerCharacter;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    private ChallengeService challengeService;
    public static Player player1, player2;
    public static PlayerCharacter playerCharacter1, playerCharacter2;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeService();

        playerCharacter1 = new PlayerCharacter();
        playerCharacter1.setId("00000000-0000-0000-0000-000000000000");
        playerCharacter1.setGold(500);
        playerCharacter1.getDocument().saveToDatabase(PlayerCharacter.class);

        player1 = new Player();
        player1.setId("A12BC");
        player1.setCharacter(playerCharacter1);
        player1.getDocument().saveToDatabase(player1.getClass());

        playerCharacter2 = new PlayerCharacter();
        playerCharacter2.setId("00000000-0000-0000-0000-000000000001");
        playerCharacter2.setGold(500);
        playerCharacter2.getDocument().saveToDatabase(PlayerCharacter.class);

        player2 = new Player();
        player2.setId("D13EF");
        player2.setCharacter(playerCharacter2);
        player2.getDocument().saveToDatabase(player2.getClass());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateChallenge() {
        ChallengeRequest challengeRequest = new ChallengeRequest("A12BC", "D13EF", 100);
        ResponseBody response = challengeService.createChallenge(challengeRequest);
        assertTrue(response.ok);
    }


    @Test
    void testGetChallenge() {
        ChallengeRequest challengeRequest = new ChallengeRequest("A12BC", "D13EF", 100);
        String id = challengeRequest.getId();
        ResponseBody response = challengeService.getChallenge(id);
        assertTrue(response.ok);
    }

    @Test
    void testAcceptChallengeFromPlayer() {
        ChallengeRequest challengeRequest = new ChallengeRequest("A12BC", "D13EF", 100);
        ResponseBody response = challengeService.acceptChallengeFromPlayer(challengeRequest);
        assertTrue(response.ok);
    }

    @Test
    void testAcceptChallengeFromOperator() {
        ChallengeRequest challengeRequest = new ChallengeRequest("A12BC", "D13EF", 100);
        ResponseBody response = challengeService.acceptChallengeFromOperator(challengeRequest);
        assertTrue(response.ok);
    }

    @Test
    void testDenyChallengeFromPlayer() {
        ChallengeRequest challengeRequest = new ChallengeRequest("A12BC", "D13EF", 100);
        ResponseBody response = challengeService.denyChallengeFromPlayer(challengeRequest);
        assertTrue(response.ok);
    }

    @Test
    void testAddIdToPlayer() {
        String challengeId = "12345678";
        ResponseBody response = challengeService.addIdToPlayer(challengeId, player1.getNick(), "pendingDuels");
        assertTrue(response.ok);

    }

    @Test
    void testRemoveIdFromPlayer() {
        ChallengeRequest challengeRequest = new ChallengeRequest("A12BC", "D13EF", 100);
        String challengeId = "12345678";
        challengeRequest.setId(challengeId);

        player1.addPending(challengeRequest);
        player1.getDocument().saveToDatabase(Player.class);
        ResponseBody response = challengeService.removeIdFromPlayer(challengeId, player1.getNick(), "pendingDuels");
        assertTrue(response.ok);
    }


    @Test
    void testGetRanking() {
        ResponseBody response = challengeService.getRanking();
        assertTrue(response.ok);
    }

    @Test
    void testGetOperatorChallenges() {
        ResponseBody response = challengeService.getOperatorChallenges();
        assertTrue(response.ok);
    }
}
