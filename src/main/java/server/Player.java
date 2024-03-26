package server;

import server.characters.Character;
import server.characters.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
public class Player extends User{
=======
public class Player extends User {
    private String id;
>>>>>>> Stashed changes
    private List<ChallengeRequest> pendingDuels = new ArrayList<>();
    private List<ChallengeResult> results = new ArrayList<>();
    private PlayerCharacter character;
    private boolean blocked;

<<<<<<< Updated upstream
    public void sendChallenge (Player target, int bet){
=======
    public Player() {
        super();
    }
    public Player(String name, String nick, String password) {
        super(name, nick, password);
    }

    private void sendChallenge (Player target, int bet){
>>>>>>> Stashed changes
        if (target.getCharacter().getGold() >= bet) {
            ChallengeRequest request = new ChallengeRequest(this, target, this.character);
            //MARCELO TÓCAMELO añade request a la database
            return;
        }
        //no bro
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

}
