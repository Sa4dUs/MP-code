package api;

import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class RequestHandler extends Handler<BiFunction<String, RequestBody, ResponseBody>> {
    private final Map<String, Handler<BiFunction<String, RequestBody, ResponseBody>>> endpointHandlers = new HashMap<>();

    public RequestHandler() {
        endpointHandlers.put(Endpoint.AUTH, new AutenticationHandler());
        endpointHandlers.put(Endpoint.CHARACTER, new CharacterHandler());
        endpointHandlers.put(Endpoint.CHALLENGE, new ChallengeHandler());
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

        Handler<BiFunction<String, RequestBody, ResponseBody>> handler = endpointHandlers.getOrDefault(route.pop(), null);

        return handler == null ? null : handler.request(route.pop(), body);
    }
}
