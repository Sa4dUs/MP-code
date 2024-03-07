package api;

import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;

import java.util.HashMap;

public class RequestHandler implements Handler {
    private final String AUTH_ENDPOINT = "auth";
    private final String CHARACTER_ENDPOINT = "character";
    private final String CHALLENGE_ENDPOINT = "challenge";

    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

        Handler handler;

        switch (route.pop()) {
            case AUTH_ENDPOINT:
                handler = new AutenticationHandler();
                break;
            case CHARACTER_ENDPOINT:
                handler = new CharacterHandler();
                break;
            case CHALLENGE_ENDPOINT:
                handler = new ChallengeHandler();
                break;
            default:
                System.out.println("[RequestHandler.java] 404: Endpoint not found");
                return null;
        }

        return handler.request(route.pop(), body);
    }
}
