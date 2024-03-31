package lib;

import java.util.HashMap;
import java.util.Map;

public class ResponseBody extends Body {
    public Boolean ok;

    public ResponseBody() {this.ok = false;}
    public ResponseBody(Boolean ok) {
        super();
        this.ok = ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
