import server.ChallengeRequest;
import server.ChallengeResult;
import server.Player;
import server.characters.PlayerCharacter;

public class ResultTest {
    public void testDuelResult()
    {
        Player p1 = new Player();
        Player p2 = new Player();

        PlayerCharacter attCharacter = new PlayerCharacter();
        PlayerCharacter defCharacter = new PlayerCharacter();

        ChallengeRequest request = new ChallengeRequest(p1, p2, attCharacter);
        ChallengeResult res = new ChallengeResult(request, defCharacter);

    }
}
