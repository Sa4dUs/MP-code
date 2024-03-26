package client;

import api.RequestHandler;
import lib.RequestBody;
import lib.ResponseBody;

public class Client {
    private static Client instance;
    private final RequestHandler handler;

    private Client() {
        handler = new RequestHandler();
    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public static ResponseBody request(String endpoint, RequestBody body) {
        return getInstance().handler.request(endpoint, body);
    }
}
