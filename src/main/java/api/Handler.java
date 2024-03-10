package api;

import server.services.Service;
import lib.RequestBody;
import lib.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public abstract class Handler<T> {
    Service service = null;
<<<<<<< Updated upstream
    Map<String, T> operations = null;
=======
    Map<String, T> operations = new HashMap<>();
>>>>>>> Stashed changes

    public abstract ResponseBody request(String endpoint, RequestBody body);
}
