package client;

import api.RequestHandler;
import lib.RequestBody;
import lib.ResponseBody;

public class Client {
    private static Client instance;
    private final RequestHandler handler;

    public Client() {
        handler = new RequestHandler();

        if (instance == null) {
            instance = this;
        }
    }

    public static ResponseBody request(String endpoint, RequestBody body) {
        return instance.handler.request(endpoint, body);
    }
}
