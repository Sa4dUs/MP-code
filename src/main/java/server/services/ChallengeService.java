package server.services;

import jdk.javadoc.doclet.Reporter;
import lib.ResponseBody;
import server.ChallengeRequest;

public class ChallengeService implements Service {

    public ResponseBody createChallenge(ChallengeRequest challenge) {
        return new ResponseBody(true);
    }

    public ResponseBody acceptChallenge(ChallengeRequest challenge) {
        return new ResponseBody(true);
    }

    public ResponseBody denyChallenge(ChallengeRequest challenge) {
        return new ResponseBody(true);
    }
}
