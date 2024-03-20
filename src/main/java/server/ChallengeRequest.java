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

    public void accept()
    {
        ChallengeResult result = new ChallengeResult(this, this.attackedPlayer.getCharacter());
        this.attackedPlayer.addResult(result);
        this.attackingPlayer.addResult(result);
        this.attackedPlayer.deletePendingChallenge(this);
        //MARCELO TÓCAMELO X2
    }

    public void denyFromPlayer()
    {
        ChallengeResult result = new ChallengeResult(this);
        this.attackedPlayer.addResult(result);
        this.attackingPlayer.addResult(result);
        this.attackedPlayer.deletePendingChallenge(this);
        //MARCELO TÓCAMELO X3
    }

    public void sendToTarget()
    {
        this.attackedPlayer.addPending(this);
        //MARCELO TÓCAMELO X4 -> quítalo de la base de datos o algo ns xd
    }

    public void denyFromOperator()
    {
        this.attackedPlayer.deletePendingChallenge(this);
        this.attackingPlayer.deletePendingChallenge(this);
        //podemos notificar al usuario "attacking" de que su solicitud de duelo ha sido denegada

        //MARCELO TÓCAMELO Y JORGE TÓCAMELO :)
    }

    public Player getAttackingPlayer() {
        return this.attackingPlayer;
    }

    public void setAttackingPlayer(Player attackingPlayer) {
        this.attackingPlayer = attackingPlayer;
    }

    public Player getAttackedPlayer() {
        return this.attackedPlayer;
    }

    public void setAttackedPlayer(Player attackedPlayer) {
        this.attackedPlayer = attackedPlayer;
    }

    public PlayerCharacter getAttackingCharacter() {
        return this.attackingCharacter;
    }

    public void setAttackingCharacter(PlayerCharacter attackingCharacter) {
        this.attackingCharacter = attackingCharacter;
    }

    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

}
