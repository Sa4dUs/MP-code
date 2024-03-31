package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;

import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private final Map<String, Handler> operations;
    public RequestHandler() {
        this.operations = new HashMap<>();
        this.operations.put(Endpoint.AUTH, new AuthenticationHandler());
        this.operations.put(Endpoint.CHARACTER, new CharacterHandler());
        this.operations.put(Endpoint.CHALLENGE, new ChallengeHandler());
        this.operations.put(Endpoint.ITEM, new ItemHandler());
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

        Handler handler = this.operations.getOrDefault(route.pop(), null);

        return handler == null ? null : handler.request(route.pop(), body);
    }
}
