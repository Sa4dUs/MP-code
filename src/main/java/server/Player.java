package server;

import server.characters.Character;
import server.characters.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class Player extends User{
    private String id;
    private List<ChallengeRequest> pendingDuels = new ArrayList<>();
    private List<ChallengeResult> results = new ArrayList<>();
    private PlayerCharacter character;
    private boolean blocked;

    private void sendChallenge (Player target, int bet){
        if (target.getCharacter().getGold() >= bet) {
            ChallengeRequest request = new ChallengeRequest(this, target, this.character);
            //MARCELO TÓCAMELO
            return;
        }
        //no bro
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }

    public void addResult(ChallengeResult result) {
        results.add(result);
    }

    public void deletePendingChallenge(ChallengeRequest pending){
        pendingDuels.remove(pending);
    }

    public void addPending(ChallengeRequest request) {
        pendingDuels.add(request);
    }

    public List<ChallengeRequest> getPendingDuels() {
        return pendingDuels;
    }

    public List<ChallengeResult> getResults() {
        return results;
    }

}
