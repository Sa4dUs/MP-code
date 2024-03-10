package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;

<<<<<<< Updated upstream
public class RequestHandler extends Handler<Function<RequestBody, ResponseBody>> {
    private final Map<String, Handler<Function<RequestBody, ResponseBody>>> endpointHandlers = new HashMap<>();

    public RequestHandler() {
        endpointHandlers.put(Endpoint.AUTH, new AutenticationHandler());
        endpointHandlers.put(Endpoint.CHARACTER, new CharacterHandler());
        endpointHandlers.put(Endpoint.CHALLENGE, new ChallengeHandler());
=======
public class RequestHandler extends Handler<Handler<NFunction<ResponseBody>>> {

    public RequestHandler() {
        this.operations.put(Endpoint.AUTH, new AuthenticationHandler());
        this.operations.put(Endpoint.CHARACTER, new CharacterHandler());
        this.operations.put(Endpoint.CHALLENGE, new ChallengeHandler());
>>>>>>> Stashed changes
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

<<<<<<< Updated upstream
        Handler<Function<RequestBody, ResponseBody>> handler = endpointHandlers.getOrDefault(route.pop(), null);
=======
        Handler<NFunction<ResponseBody>> handler = this.operations.getOrDefault(route.pop(), null);
>>>>>>> Stashed changes

        return handler == null ? null : handler.request(route.pop(), body);
    }
}
