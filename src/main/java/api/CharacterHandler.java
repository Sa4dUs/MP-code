package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import api.Handler;

import java.util.function.Function;

public class CharacterHandler extends Handler<NFunction<ResponseBody>> {
    public ResponseBody request(String endpoint, RequestBody body) {

        return new ResponseBody();
    }
}