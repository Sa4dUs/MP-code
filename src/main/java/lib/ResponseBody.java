package lib;

import java.util.HashMap;
import java.util.Map;

public class ResponseBody extends Body {
    public Boolean ok;

    public ResponseBody() {}
    public ResponseBody(Boolean ok) {
        this.ok = ok;
    }
}
