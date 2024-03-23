package api;

import lib.NFunction;
import server.services.Service;
import lib.RequestBody;
import lib.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public abstract class Handler {
    Map<String, NFunction<ResponseBody, RequestBody>> operations = new HashMap<>();

    public ResponseBody request(String endpoint, RequestBody body) {
        try {
            return this.operations.getOrDefault(endpoint, args -> {
                // Handle invalid route, for now it will return ok = false
                return new ResponseBody(false);
            }).apply(body);
        } catch (Exception e) {
            return new ResponseBody(false);
        }

    }
}
