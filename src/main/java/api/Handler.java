package api;

import server.services.Service;
import lib.RequestBody;
import lib.ResponseBody;

public interface Handler {
    Service service = null;

    public ResponseBody request(String endpoint, RequestBody body);
}
