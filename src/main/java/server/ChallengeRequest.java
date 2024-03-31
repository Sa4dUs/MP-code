package server;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.ChallengeRequestSchema;

public class ChallengeRequest implements JSONable {
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

    @Override
    public Document getDocument() {
        Document document = new Document(new ChallengeRequestSchema());
        if(this.id != null)
            document.setProperty("id", this.id);
        else
            this.id = document.getId();
        document.setProperty("bet", this.bet);
        document.setProperty("attackerId", this.attackerId);
        document.setProperty("attackerId", this.attackedId);

        return document;
    }
}
