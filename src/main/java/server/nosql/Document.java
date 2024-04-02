package server.nosql;

import lib.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import server.Crypto;
import server.Database;

import java.lang.reflect.*;
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
        } else if (targetType.equals(Boolean.class))
        {
            return Boolean.toString((Boolean) value);
        }else if ((targetType.isArray()  && targetType.getComponentType().equals(String.class)))
        {
            return value;
        }else if (targetType.equals(JSONArray.class))
        {
            try {
                JSONArray jsonArray = (JSONArray) value;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = jsonArray.getString(i);
                    list.add(id);
                }
                return list.toArray(new String[0]);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid JSONArray value: " + value);
            }
        }else {
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

    public Object deJSONDocument(Class<?> clazz)
    {
        try {
            Object object = clazz.getDeclaredConstructor().newInstance();
            if(object == "null")
                throw new RuntimeException("Class: " + clazz.getName() + "does not have an empty constructor");
            Class<?> currentClass = clazz;
            while (currentClass != Object.class) {
                for (Field field : currentClass.getDeclaredFields()) {

                    Class<?> fieldType = field.getType();
                    String fieldName = field.getName();
                    Object property = this.getProperty(fieldName);

                    if (property == null)
                        continue;

                    field.setAccessible(true);

                    if (fieldType.isPrimitive() || fieldType == String.class)
                    {
                        if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class))
                            field.set(object, Boolean.valueOf((String) property));
                        else
                            field.set(object, property);
                    }
                    else if (fieldType.isArray())
                    {
                        field.set(object, getObjectArrayFromDoc((String[]) property, fieldType.getComponentType()));
                    }
                    else if (List.class.isAssignableFrom(fieldType))
                    {
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        Class<?> elementType = (Class<?>) parameterizedType.getActualTypeArguments()[0];

                        field.set(object, new ArrayList<>(Arrays.asList(getObjectArrayFromDoc((String[]) property, elementType))));
                    }
                    else if (fieldType.isEnum())
                    {
                        Enum<?>[] enumValues = (Enum<?>[]) fieldType.getEnumConstants();
                        field.set(object, (enumValues[(Integer) property]));
                    }
                    else
                    {
                        field.set(object, getObjectFromDoc((String) property, fieldType));
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
            return object;

        } catch (Exception e) {
            throw new RuntimeException("Error creating object: " + clazz.getName() + e);
        }
    }

    public static Object[] getObjectArrayFromDoc(String[] ids, Class<?> clazz){
        List<Object> list = new ArrayList<>();

        Class<?> currentClass = clazz;
        for (String id: ids)
        {
            Document document = getDocument(id, currentClass);
            if(document == null)
                try {
                    Method method = clazz.getMethod("getSubClasses");
                    List<Class<?>> subclasses =(List<Class<?>>) method.invoke(null);
                    for (Class<?> subClass : subclasses) {
                        Document subDocument = getDocument(id, subClass);
                        if (subDocument != null) {
                            list.add(subDocument.deJSONDocument(subClass));
                            break;
                        }
                    }
                }catch (Exception e)
                {
                    continue;
                }
            else
                list.add(document.deJSONDocument(currentClass));
        }
        return list.toArray(new Object[0]);
    }

    public static Object getObjectFromDoc(String id, Class<?> clazz) {

        Document document = getDocument(id, clazz);
        if(document == null)
            try {
                Method method = clazz.getMethod("getSubClasses");
                List<Class<?>> subclasses =(List<Class<?>>) method.invoke(null);
                for (Class<?> subClass : subclasses) {
                    Document subDocument = getDocument(id, subClass);
                    if (subDocument != null) {
                        return subDocument.deJSONDocument(subClass);
                    }
                }
            }catch (Exception e)
            {
                return null;
            }

        if(document == null)
            return null;
        return document.deJSONDocument(clazz);
    }

    public static Document getDocument(String id, Class<?> clazz)
    {
        Query query = new Query();
        query.addFilter("id", id);

        return Database.findOne(clazz.getName(), query);
    }

    public void saveToDatabase(Class<?> clazz)
    {
        Query query = new Query();
        query.addFilter("id", this.getId());
        if(Database.findOne(clazz.getName(), query) == null)
            Database.insertOne(clazz.getName(), this);

        else
            Database.updateOne(clazz.getName(), this, query);

    }
}