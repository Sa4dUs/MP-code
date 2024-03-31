package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.characters.PlayerCharacter;
import server.services.AuthenticationService;
import server.services.ChallengeService;

public class ChallengeHandler extends Handler {
    private ChallengeService service = null;
    public ChallengeHandler() {
        this.service = new ChallengeService();
        this.operations.put(null, req -> new ResponseBody());
        this.operations.put("create", req -> this.service.createChallenge((ChallengeRequest) req.getField("challenge")));

        this.operations.put("acceptChallengeFromOperator", req -> this.service.acceptChallengeFromOperator((ChallengeRequest) req.getField("challenge")));
        this.operations.put("denyChallengeFromOperator", req -> this.service.denyChallengeFromOperator((ChallengeRequest) req.getField("challenge")));

        this.operations.put("acceptChallengeFromPlayer", req -> this.service.acceptChallengeFromPlayer((ChallengeRequest) req.getField("challenge")));
        this.operations.put("denyChallengeFromPlayer", req -> this.service.denyChallengeFromPlayer((ChallengeRequest) req.getField("challenge")));

        this.operations.put("addIdToPlayer", req -> this.service.addIdToPlayer((String) req.getField("id"), (String) req.getField("nick"), (String) req.getField("property")));
        this.operations.put("removeIdFromPlayer", req -> this.service.removeIdFromPlayer((String) req.getField("id"), (String) req.getField("nick"), (String) req.getField("property")));

        this.operations.put("getChallenge", req -> this.service.getChallenge((String) req.getField("id")));

        this.operations.put("ranking", req -> this.service.getRanking());
    }
}
