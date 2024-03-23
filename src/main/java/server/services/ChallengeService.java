package server.services;

import jdk.javadoc.doclet.Reporter;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.Database;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Schemas.ChallengeRequestSchema;

public class ChallengeService implements Service {

    public ResponseBody createChallenge(ChallengeRequest challenge)
    {
        Document doc = new Document(new ChallengeRequestSchema());
        Database.insertOne(Collection.CHALLENGE, doc);
        return new ResponseBody(true);
    }

    public ResponseBody acceptChallenge(ChallengeRequest challenge) {
        return new ResponseBody(true);
    }

    public ResponseBody denyChallenge(ChallengeRequest challenge) {
        return new ResponseBody(true);
    }
}
