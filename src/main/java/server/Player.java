package server;

import server.characters.PlayerCharacter;

import java.util.List;

public class Player extends User{
    private String id;
    private List<ChallengeRequest> pendingDuels;
    private List<ChallengeResult> pendingResults;
    private PlayerCharacter character;
    private boolean blocked;
}
