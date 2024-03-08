package api;

import lib.RequestBody;
import lib.ResponseBody;
import api.Handler;

import java.util.function.Function;

public class AutenticationHandler extends Handler<Function<RequestBody, ResponseBody>> {
    AutenticationHandler () {

    }

    public ResponseBody request(String endpoint, RequestBody body) {

        return new ResponseBody();
    }
}
