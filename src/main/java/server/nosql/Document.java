package server.nosql;

import lib.ResponseBody;
import org.json.JSONObject;
import server.Crypto;
import server.Database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

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

    public static Object deJSONDocument(Document document, Class<?> clazz)
    {
        try {
            Object object = clazz.getDeclaredConstructor().newInstance();

            for(Field field: clazz.getDeclaredFields())
            {
                if(field.getType().isPrimitive())
                {
                    if(field.getType().equals(Boolean.class) || field.getType().equals(boolean.class))
                        field.set(object, Boolean.valueOf((String) document.getProperty(field.getName())));
                    else
                        field.set(object, document.getProperty(field.getName()));
                }
                else if(field.getType().isArray())
                {
                    field.set(object, getObjectArrayFromDoc((String[]) document.getProperty(field.getName()), field.getClass()));
                }
                else if(List.class.isAssignableFrom(field.getType()))
                {
                    field.set(object, List.of((Object[]) getObjectArrayFromDoc((String[]) document.getProperty(field.getName()), field.getClass())));
                }
                else
                {
                    field.set(object, List.of(getObjectFromDoc((String) document.getProperty(field.getName()), field.getClass())));
                }
            }

            return object;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] getObjectArrayFromDoc(String[] ids, Class<?> clazz){
        List<Object> list = new ArrayList<>();

        for (String id: ids)
        {
            Document document = getDocument(id, clazz);
            if(document == null)
                continue;

            list.add(deJSONDocument(document, clazz));
        }
        return list.toArray(new Object[0]);
    }

    public static Object getObjectFromDoc(String id, Class<?> clazz) {

        Document document = getDocument(id, clazz);
        ResponseBody responseBody = new ResponseBody(true);
        responseBody.addField(id, deJSONDocument(document, clazz));
        return responseBody;
    }

    public static Document getDocument(String id, Class<?> clazz)
    {
        Query query = new Query();
        query.addFilter("id", id);

        return Database.findOne(clazz.getName(), query);
    }
}