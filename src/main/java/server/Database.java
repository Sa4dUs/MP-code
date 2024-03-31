package server;

import org.json.JSONObject;
import server.nosql.Document;
import server.nosql.Query;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static lib.JSON.*;

public class Database {
    private static final String PATH = "./src/main/resources/";

    public Database() {
    }

    public static Document findOne(String collection, Query query) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            for (String key : jsonObject.keySet()) {
                JSONObject docObject = jsonObject.getJSONObject(key);
                Document document = Document.fromJSON(docObject.toString());
                if (query.matches(document)) {
                    return document;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<Document> findMany(String collection, Query query) {
        List<Document> result = new ArrayList<>();
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            for (String key : jsonObject.keySet()) {
                JSONObject docObject = jsonObject.getJSONObject(key);
                Document document = Document.fromJSON(docObject.toString());
                if (query.matches(document)) {
                    result.add(document);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void insertOne(String collection, Document document) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            jsonObject.put(document.getId(), new JSONObject(document.toJSON()));
            writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertMany(String collection, List<Document> documents) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            for (Document doc : documents) {
                jsonObject.put(doc.getId(), new JSONObject(doc.toJSON()));
            }
            writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateOne(String collection, Document document, Query query) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            for (String key : jsonObject.keySet()) {
                JSONObject docObject = jsonObject.getJSONObject(key);
                Document existingDoc = Document.fromJSON(docObject.toString());
                if (query.matches(existingDoc)) {
                    existingDoc.updateFromDocument(document);
                    jsonObject.put(key, new JSONObject(existingDoc.toJSON()));
                    writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateMany(String collection, Document document, Query query) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            for (String key : jsonObject.keySet()) {
                JSONObject docObject = jsonObject.getJSONObject(key);
                Document existingDoc = Document.fromJSON(docObject.toString());
                if (query.matches(existingDoc)) {
                    existingDoc.updateFromDocument(document);
                    jsonObject.put(key, new JSONObject(existingDoc.toJSON()));
                }
            }
            writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteOne(String collection, Query query) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            for (String key : jsonObject.keySet()) {
                JSONObject docObject = jsonObject.getJSONObject(key);
                Document document = Document.fromJSON(docObject.toString());
                if (query.matches(document)) {
                    jsonObject.remove(key);
                    writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteMany(String collection, Query query) {
        try {
            JSONObject jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
            List<String> keysToRemove = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                JSONObject docObject = jsonObject.getJSONObject(key);
                Document document = Document.fromJSON(docObject.toString());
                if (query.matches(document)) {
                    keysToRemove.add(key);
                }
            }
            for (String key : keysToRemove) {
                jsonObject.remove(key);
            }
            writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}