package server;

import org.json.Property;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.PlayerSchema;
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

    public Player(String name, String nick, String password) {
        super(name, password);
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

    public void changeGold(int amount){this.character.setGold(this.character.getGold() + amount);}

    @Override
    public Document getDocument() {
        Document document = new Document(new PlayerSchema());
        document.updateFromDocument(super.getDocument());

        document.setProperty("id", this.getNick());

        document.setProperty("pendingDuels", getIdArrayFromArray(pendingDuels.toArray(new JSONable[0])));
        document.setProperty("results", getIdArrayFromArray(results.toArray(new JSONable[0])));

        if(this.character != null) {
            if(this.character.getId() == null)
                this.character.getDocument().saveToDatabase(PlayerCharacter.class);
            document.setProperty("character", this.character.getId());
        }
        else
            document.setProperty("character", "");

        document.setProperty("blocked", this.blocked);
        document.setProperty("pending", this.pending);

        return document;
    }

    private String[] getIdArrayFromArray(JSONable[] objects)
    {
        List<String> res = new ArrayList<>();
        for(JSONable object: objects)
        {
            Document objectDoc = object.getDocument();
            objectDoc.saveToDatabase(object.getClass());
            res.add(objectDoc.getId());
        }

        return res.toArray(new String[0]);
    }
}
