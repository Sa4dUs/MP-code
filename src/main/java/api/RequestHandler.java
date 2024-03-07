package api;

import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;
import api.Endpoint;

import java.util.HashMap;

public class RequestHandler implements Handler {
    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

        Handler handler;

        switch (route.pop()) {
            case Endpoint.AUTH:
                handler = new AutenticationHandler();
                break;
            case Endpoint.CHARACTER:
                handler = new CharacterHandler();
                break;
            case Endpoint.CHALLENGE:
                handler = new ChallengeHandler();
                break;
            default:
                System.out.println("[RequestHandler.java] 404: Endpoint not found");
                return null;
        }

        return handler.request(route.pop(), body);
    }
}
