package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import server.services.AuthenticationService;

public class AuthenticationHandler extends Handler<NFunction<ResponseBody>> {
    private final AuthenticationService service;
    AuthenticationHandler() {
        this.service = new AuthenticationService();
        // For base route, might delete later
        this.operations.put(null, args -> new ResponseBody());
        this.operations.put("login", args -> {
            if (!(args[0] instanceof RequestBody)) {
                return new ResponseBody(false);
            }

            return service.login((String) ((RequestBody) args[0]).getField("username"), (String) ((RequestBody) args[0]).getField("password"));
        });
        this.operations.put("signup", args -> {
            if (!(args[0] instanceof RequestBody)) {
                return new ResponseBody(false);
            }

            return service.signup((String) ((RequestBody) args[0]).getField("username"), (String) ((RequestBody) args[0]).getField("password"));
        });
    }

    public ResponseBody request(String endpoint, RequestBody body) {
        return this.operations.getOrDefault(endpoint, args -> {
            // Handle invalid route, for now it will return null
            return new ResponseBody(false);
        }).apply(body);
    }
}