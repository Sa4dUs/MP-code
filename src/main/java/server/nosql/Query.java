package server.nosql;

import server.nosql.Document;

import java.util.HashMap;
import java.util.Map;

public class Query {
    private final Map<String, Object> queryMap;

    public Query() {
        this.queryMap = new HashMap<>();
    }

    public void addFilter(String key, Object value) {
        queryMap.put(key, value);
    }

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public boolean matches(Document document) {
        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!document.containsKey(key) || !document.getProperty(key).equals(value)) {
                return false;
            }
        }
        return true;
    }
}