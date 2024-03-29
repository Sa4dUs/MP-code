package server.nosql;

import org.json.JSONObject;
import server.Crypto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
    private final Schema schema;
    private final Map<String, Object> properties;

    public Document() {
        this.schema = null;
        this.properties = new HashMap<String, Object>();
    }

    public Document(Schema schema) {
        this.schema = schema;
        properties = new HashMap<>();
        properties.put("id", Crypto.UUIDv4());
    }

    public Document(JSONObject jsonObject) {
        this.schema = new Schema(jsonObject);
        properties = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            setProperty(key, value);
        }
    }

    public void setProperty(String key, Object value) {
        Class<?> expectedType = schema.getType(key);
        if (expectedType == null) {
            throw new IllegalArgumentException("Field " + key + " is not defined in schema");
        }

        Object parsedValue;
        try {
            parsedValue = parseValue(value, expectedType);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for field " + key + ": " + e.getMessage());
        }

        properties.put(key, parsedValue);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public void updateFromDocument(Document otherDocument) {
        for (String key : otherDocument.properties.keySet()) {
            properties.put(key, otherDocument.getProperty(key));
        }
    }

    private Object parseValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        if (targetType.equals(String.class)) {
            return value.toString();
        } else if (targetType.equals(Integer.class)) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Integer value: " + value);
            }
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + targetType.getName());
        }
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject(properties);
        return jsonObject.toString();
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public String getId() {
        return (String) this.properties.get("id");
    }

    public static Document fromJSON(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return new Document(jsonObject);
    }

    public static void setFieldsFromDocument(Object object, Document document)
    {
        for(Field field: object.getClass().getDeclaredFields())
        {
            try
            {
                if(field.getType().equals(List.class))
                    field.set(object, List.of(document.getProperty(field.getName())));
                else
                    field.set(object, document.getProperty(field.getName()));
            } catch (Exception e)
            {
                if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL)
                    continue;

                throw new RuntimeException("Document " + document + "cannot be casted into " + object);
            }
        }
    }
}