package api;

import server.services.Service;
import lib.RequestBody;
import lib.ResponseBody;

import java.util.Map;

public abstract class Handler<T> {
    Service service = null;
    Map<String, T> operations = null;

    public abstract ResponseBody request(String endpoint, RequestBody body);
}
