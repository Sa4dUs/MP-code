package server.nosql;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Schema {
    protected Map<String, Class<?>> schema;

    public Schema() {
        this.schema = new HashMap<String, Class<?>>();
    }
    public Schema(Map<String, Class<?>> schema) {
        this();
        this.schema = schema;
    }

    public Schema(JSONObject jsonObject) {
        this();
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = keys.next();

            schema.put(key, jsonObject.get(key).getClass());
        }
    }

    public Class<?> getType(String fieldName) {
        return schema.get(fieldName);
    }
}
