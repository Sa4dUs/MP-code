package server;

import server.nosql.Document;

public class ChallengeRequest {
    private String id;
    private String attackerId, attackedId;
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
        this.attackerId = attackingPlayerId;
        this.attackedId = attackedPlayerId;
        this.bet = bet;
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

    public String getAttackerId() {
        return this.attackerId;
    }

    public void setAttackerId(String attackerId) {
        this.attackerId = attackerId;
    }

    public String getAttackedId() {
        return this.attackedId;
    }

    public void setAttackedId(String attackedId) {
        this.attackedId = attackedId;
    }

    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

}
