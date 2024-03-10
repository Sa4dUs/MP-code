package lib;

import java.util.HashMap;
import java.util.Map;

public class Body {
    private Map<String, Object> data = new HashMap<>();

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addField(String key, Object value) {
        this.data.put(key, value);
    }

    public Object getField(String key) {
        return this.data.get(key);
    }
}
