package api;

import lib.RequestBody;
import lib.ResponseBody;
import java.util.function.Function;

public class ChallengeHandler extends Handler<Function<RequestBody, ResponseBody>> {
    public ResponseBody request(String endpoint, RequestBody body) {

        return new ResponseBody();
    }
}
