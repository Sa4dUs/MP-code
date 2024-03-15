package server;

import org.json.JSONArray;
import org.json.JSONObject;
import server.nosql.Document;
import server.nosql.Query;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lib.JSON.*;

public class Database {

    public static Database instance;
    private static final String PATH = "./src/main/resources/";
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final Map<String, JSONObject> collectionCache = new ConcurrentHashMap<>();

    public Database() {
        if (instance != null)
            instance = this;
    }

    public static Document findOne(String collection, Query query) {
        JSONObject jsonObject = getCachedJSONObject(collection);
        JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);

        for (int i = 0; i < jsonArray.length(); i++) {
            Document existingDoc = new Document(jsonArray.getJSONObject(i));
            if (query.matches(existingDoc)) {
                return existingDoc;
            }
        }

        return null;
    }

    public static List<Document> findMany(String collection, Query query) {
        List<Document> result = new ArrayList<>();

        JSONObject jsonObject = getCachedJSONObject(collection);
        JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);

        for (int i = 0; i < jsonArray.length(); i++) {
            Document existingDoc = new Document(jsonArray.getJSONObject(i));
            if (query.matches(existingDoc)) {
                result.add(existingDoc);
            }
        }

        return result;
    }

    public static void insertOne(String collection, Document document) {
        String json = document.toJSON();
        JSONObject jsonObject = getCachedJSONObject(collection);

        JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);
        jsonArray.put(new JSONObject(json));
        jsonObject.put(collection, jsonArray);
        updateCache(collection, jsonObject);
        executeWriteTask(collection, jsonObject);
    }

    public static void insertMany(String collection, List<Document> documents) {
        JSONObject jsonObject = getCachedJSONObject(collection);
        JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);

        for (Document doc : documents) {
            jsonArray.put(new JSONObject(doc.toJSON()));
        }

        jsonObject.put(collection, jsonArray);
        updateCache(collection, jsonObject);
        executeWriteTask(collection, jsonObject);
    }

    public static void updateOne(String collection, Document document, Query query) {
        JSONObject jsonObject = getCachedJSONObject(collection);

        JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);

        for (int i = 0; i < jsonArray.length(); i++) {
            Document existingDoc = new Document(jsonArray.getJSONObject(i));
            if (query.matches(existingDoc)) {
                existingDoc.updateFromDocument(document);
                jsonArray.put(i, new JSONObject(existingDoc.toJSON()));
                break;
            }
        }

        jsonObject.put(collection, jsonArray);
        updateCache(collection, jsonObject);
        executeWriteTask(collection, jsonObject);
    }

    public static void updateMany(String collection, Document document, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);
            JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);

            for (int i = 0; i < jsonArray.length(); i++) {
                Document existingDoc = new Document(jsonArray.getJSONObject(i));
                if (query.matches(existingDoc)) {
                    existingDoc.updateFromDocument(document);
                    jsonArray.put(i, new JSONObject(existingDoc.toJSON()));
                }
            }

            jsonObject.put(collection, jsonArray);
            updateCache(collection, jsonObject);
            executeWriteTask(collection, jsonObject);
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    public static void deleteOne(String collection, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);
            JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);

            for (int i = 0; i < jsonArray.length(); i++) {
                Document existingDoc = new Document(jsonArray.getJSONObject(i));
                if (query.matches(existingDoc)) {
                    jsonArray.remove(i);
                    break;
                }
            }

            jsonObject.put(collection, jsonArray);
            updateCache(collection, jsonObject);
            executeWriteTask(collection, jsonObject);
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    public static void deleteMany(String collection, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);
            JSONArray jsonArray = getJSONArrayFromJSONObject(jsonObject, collection);
            JSONArray updatedArray = new JSONArray();

            for (int i = 0; i < jsonArray.length(); i++) {
                Document existingDoc = new Document(jsonArray.getJSONObject(i));
                if (!query.matches(existingDoc)) {
                    updatedArray.put(jsonArray.getJSONObject(i));
                }
            }

            jsonObject.put(collection, updatedArray);
            updateCache(collection, jsonObject);
            executeWriteTask(collection, jsonObject);
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }


    private static JSONObject getCachedJSONObject(String collection) {
        String filePath = PATH + collection + ".json";

        try {


            return collectionCache.getOrDefault(collection, readJSONObjectFromFile(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateCache(String collection, JSONObject jsonObject) {
        collectionCache.put(collection, jsonObject);
    }

    private static void executeWriteTask(String collection, JSONObject jsonObject) {
        executor.execute(() -> {
            String filePath = PATH + collection + ".json";
            writeJSONObjectToFile(jsonObject, filePath);
        });
    }
}