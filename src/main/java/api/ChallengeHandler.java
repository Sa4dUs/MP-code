package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.services.AuthenticationService;
import server.services.ChallengeService;

public class ChallengeHandler extends Handler {
    private ChallengeService service = null;
    public ChallengeHandler() {
        this.service = new ChallengeService();
        this.operations.put(null, req -> new ResponseBody());
        this.operations.put("create", req -> this.service.createChallenge((ChallengeRequest) req.getField("challenge")));
        this.operations.put("accept", req -> this.service.acceptChallenge((ChallengeRequest) req.getField("challenge")));
        this.operations.put("deny", req -> this.service.denyChallenge((ChallengeRequest) req.getField("challenge")));
    }
}
