package api;

import lib.RequestBody;
import lib.ResponseBody;
import server.services.AuthenticationService;

public class AuthenticationHandler extends Handler {
    private AuthenticationService service = null;
    AuthenticationHandler() {
        this.service = new AuthenticationService();
        // For base route, might delete later
        this.operations.put(null, body -> new ResponseBody());
        this.operations.put("login", req -> service.login((String) req.getField("username"), (String) req.getField("password")));
        this.operations.put("signup", req -> service.signup((String) req.getField("username"), (String) req.getField("password")));
        this.operations.put("delete", req -> service.delete((String) req.getField("id")));
    }
}