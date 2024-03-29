package server;

import server.characters.Character;
import server.characters.PlayerCharacter;
import server.nosql.Document;

public class ChallengeRequest {
    private String id;
    private String attackingPlayerId, attackedPlayerId;
    private int bet;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public ChallengeRequest(String attackingPlayerId, String attackedPlayerId, int bet)
    {
        this.attackingPlayerId = attackingPlayerId;
        this.attackedPlayerId = attackedPlayerId;
        this.bet = bet;
    }

    public ChallengeRequest(Document doc)
    {
        this.attackingPlayerId = (String) doc.getProperty("attackingPlayerId");
        this.attackedPlayerId = (String) doc.getProperty("attackedPlayerId");
        this.bet = (int) doc.getProperty("bet");
    }

    public void accept()
    {

    }

    public void denyFromPlayer()
    {

    }

    public void sendToTarget()
    {

    }

    public void denyFromOperator()
    {

    }

    public String getAttackingPlayerId() {
        return this.attackingPlayerId;
    }

    public void setAttackingPlayerId(String attackingPlayerId) {
        this.attackingPlayerId = attackingPlayerId;
    }

    public String getAttackedPlayerId() {
        return this.attackedPlayerId;
    }

    public void setAttackedPlayerId(String attackedPlayerId) {
        this.attackedPlayerId = attackedPlayerId;
    }

    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

}
