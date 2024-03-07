package server;

import server.characters.Character;
import server.characters.PlayerCharacter;

public class ChallengeRequest {
    private Player attackingPlayer, attackedPlayer;
    private PlayerCharacter attackingCharacter;
    private int bet;

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

    public ChallengeRequest(Player attackingPlayer, Player attackedPlayer, PlayerCharacter character)
    {
        this.attackingPlayer = attackingPlayer;
        this.attackedPlayer = attackedPlayer;
        this.attackingCharacter = character;
    }
}
