package server.nosql;

import java.util.Map;

public class Schema {
    protected Map<String, Class<?>> schema;

    public Schema(Map<String, Class<?>> schema) {
        this.schema = schema;
    }

    public Class<?> getType(String fieldName) {
        return schema.get(fieldName);
    }
}
