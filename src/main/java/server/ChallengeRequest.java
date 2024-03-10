package server;

import server.characters.Character;
import server.characters.PlayerCharacter;

public class ChallengeRequest {
    private Player attackingPlayer, attackedPlayer;
    private PlayerCharacter attackingCharacter;
    private int bet;

    public ChallengeRequest(Player attackingPlayer, Player attackedPlayer, PlayerCharacter character)
    {
        this.attackingPlayer = attackingPlayer;
        this.attackedPlayer = attackedPlayer;
        this.attackingCharacter = character;
    }

    public void accept(){
        ChallengeResult result = new ChallengeResult(this, attackedPlayer.getCharacter());
        attackedPlayer.addResult(result);
        attackingPlayer.addResult(result);
        attackedPlayer.deletePendingChallenge(this);
        //MARCELO TÓCAMELO X2
    }

    public void denyFromPlayer(){
        ChallengeResult result = new ChallengeResult(this, null);
        attackedPlayer.addResult(result);
        attackingPlayer.addResult(result);
        attackedPlayer.deletePendingChallenge(this);
        //MARCELO TÓCAMELO X3
    }

    public void sendToTarget(){
        attackedPlayer.addPending(this);
        //MARCELO TÓCAMELO X4 -> quítalo de la base de datos o algo ns xd
    }

    public void denyFromOperator(){
        ChallengeResult result = new ChallengeResult(null, null);
        //MARCELO TÓCAMELO Y JORGE TÓCAMELO :)
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public void setAttackingPlayer(Player attackingPlayer) {
        this.attackingPlayer = attackingPlayer;
    }

    public Player getAttackedPlayer() {
        return attackedPlayer;
    }

    public void setAttackedPlayer(Player attackedPlayer) {
        this.attackedPlayer = attackedPlayer;
    }

    public PlayerCharacter getAttackingCharacter() {
        return attackingCharacter;
    }

    public void setAttackingCharacter(PlayerCharacter attackingCharacter) {
        this.attackingCharacter = attackingCharacter;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

}
