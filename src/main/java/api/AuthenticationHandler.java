package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import server.services.AuthenticationService;

public class AuthenticationHandler extends Handler<NFunction<ResponseBody>> {
    AuthenticationHandler() {
        this.service = new AuthenticationService();
        // For base route, might delete later
        this.operations.put(null, new NFunction<ResponseBody>() {
            @Override
            public ResponseBody apply(Object[]... args) {
                return new ResponseBody();
            }
        });
        this.operations.put("login", new NFunction<ResponseBody>() {
            @Override
            public ResponseBody apply(Object[] ...args) {
                return null;
            }
        });
        this.operations.put("signup", null);
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        return this.operations.getOrDefault(endpoint, new NFunction<ResponseBody>() {
            @Override
            public ResponseBody apply(Object[] ...args) {
                // Handle invalid route, for now it will return null
                return null;
            }
        }).apply();
    }
}