package api;

import lib.RequestBody;
import lib.ResponseBody;
import lib.Route;

public class RequestHandler implements Handler {
    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);

        Route parser = new Route(endpoint);

        return null;
    }
}
