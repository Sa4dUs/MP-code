package server.services;

import lib.ResponseBody;

public class AuthenticationService implements Service {
    public ResponseBody login(String username, String password) {
        return new ResponseBody(true);
    }

    public ResponseBody signup(String username, String password) {
        return new ResponseBody(true);
    }
}
