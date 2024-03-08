package api;

import lib.RequestBody;
import lib.ResponseBody;
import api.Handler;

import java.util.function.BiFunction;

public class CharacterHandler extends Handler<BiFunction<String, RequestBody, ResponseBody>> {
    public ResponseBody request(String endpoint, RequestBody body) {

        return new ResponseBody();
    }
}