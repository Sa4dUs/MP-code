package api;

import lib.RequestBody;
import lib.ResponseBody;
import api.Handler;

import java.util.function.Function;

public class CharacterHandler extends Handler<Function<RequestBody, ResponseBody>> {
    public ResponseBody request(String endpoint, RequestBody body) {

        return new ResponseBody();
    }
}