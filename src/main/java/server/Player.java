package server;

import org.json.Property;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.nosql.Document;
import server.services.ChallengeService;

import javax.print.Doc;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Player extends User {
    private List<ChallengeRequest> pendingDuels = new ArrayList<>();
    private List<ChallengeResult> results = new ArrayList<>();
    private PlayerCharacter character;
    private boolean blocked, pending;

    public Player() {
        super();
    }

    public Player(String name, String nick, String password, Boolean isOperator) {
        super(name, nick, password, isOperator);
    }

    private void sendChallenge (String targetId, int bet)
    {
        ChallengeService service = new ChallengeService();
        ChallengeRequest request = new ChallengeRequest(getId(), targetId, bet);

        pending = service.createChallenge(request).ok;
    }

    public PlayerCharacter getCharacter() {
        return this.character;
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }

    public void addResult(ChallengeResult result) {
        this.results.add(result);
    }

    public void deletePendingChallenge(ChallengeRequest pending){
        this.pendingDuels.remove(pending);
    }

    public void addPending(ChallengeRequest request) {
        this.pendingDuels.add(request);
    }

    public List<ChallengeRequest> getPendingDuels() {
        return this.pendingDuels;
    }

    public List<ChallengeResult> getResults() {
        return this.results;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
