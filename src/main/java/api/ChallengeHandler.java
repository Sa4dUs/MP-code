package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import java.util.function.Function;

public class ChallengeHandler extends Handler<NFunction<ResponseBody>> {
    public ResponseBody request(String endpoint, RequestBody body) {

        return new ResponseBody();
    }
}
