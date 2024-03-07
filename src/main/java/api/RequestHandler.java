package api;

import lib.RequestBody;
import lib.ResponseBody;

public class RequestHandler implements Handler{
    public ResponseBody request(String endpoint, RequestBody body) {
        System.out.println("[RequestHandler.java] " + endpoint);
        
        return null;
    }
}
