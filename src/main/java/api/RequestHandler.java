package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;

public class RequestHandler extends Handler<Handler<NFunction<ResponseBody>>> {

    public RequestHandler() {
        this.operations.put(Endpoint.AUTH, new AuthenticationHandler());
        this.operations.put(Endpoint.CHARACTER, new CharacterHandler());
        this.operations.put(Endpoint.CHALLENGE, new ChallengeHandler());
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route route = new Route(endpoint);

        Handler<NFunction<ResponseBody>> handler = this.operations.getOrDefault(route.pop(), null);

        return handler == null ? null : handler.request(route.pop(), body);
    }
}
