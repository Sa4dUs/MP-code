package api;

import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RequestHandler extends Handler<Function<RequestBody, ResponseBody>> {

    public RequestHandler() {
        this.operations.put(Endpoint.AUTH, new AutenticationHandler());
        this.operations.put(Endpoint.CHARACTER, new CharacterHandler());
        this.operations.put(Endpoint.CHALLENGE, new ChallengeHandler());
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

        Handler<Function<RequestBody, ResponseBody>> handler = this.operations.getOrDefault(route.pop(), null);

        return handler == null ? null : handler.request(route.pop(), body);
    }
}
