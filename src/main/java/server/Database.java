package server;

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
    private static final Map<String, Map<String, Document>> collectionCache = new ConcurrentHashMap<>();

    public Database() {
        if (instance != null)
            instance = this;
    }

    public static void start() {

    }

    public static Document findOne(String collection, Query query) {
        Map<String, Document> documents = getCachedDocuments(collection);
        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                return existingDoc;
            }
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String key : jsonObject.keySet()) {
            JSONObject docObject = jsonObject.getJSONObject(key);
            Document document = Document.fromJSON(docObject.toString());
            if (query.matches(document)) {
                return document;
            }
        }
        return null;
    }

    public static List<Document> findMany(String collection, Query query) {
        List<Document> result = new ArrayList<>();
        Map<String, Document> documents = getCachedDocuments(collection);
        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                result.add(existingDoc);
            }
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String key : jsonObject.keySet()) {
            JSONObject docObject = jsonObject.getJSONObject(key);
            Document document = Document.fromJSON(docObject.toString());
            if (query.matches(document) && !result.contains(document)) {
                result.add(document);
            }
        }
        return result;
    }

    public static void insertOne(String collection, Document document) {
        Map<String, Document> documents = getCachedDocuments(collection);
        documents.put(document.getId(), document);
        updateCache(collection, documents);
        executeWriteTask(collection, documents);
    }

    public static void insertMany(String collection, List<Document> documents) {
        Map<String, Document> docMap = getCachedDocuments(collection);
        for (Document doc : documents) {
            docMap.put(doc.getId(), doc);
        }
        updateCache(collection, docMap);
        executeWriteTask(collection, docMap);
    }

    public static void updateOne(String collection, Document document, Query query) {
        Map<String, Document> documents = getCachedDocuments(collection);
        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                existingDoc.updateFromDocument(document);
                break;
            }
        }
        updateCache(collection, documents);
        executeWriteTask(collection, documents);
    }

    public static void updateMany(String collection, Document document, Query query) {
        Map<String, Document> documents = getCachedDocuments(collection);
        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                existingDoc.updateFromDocument(document);
            }
        }
        updateCache(collection, documents);
        executeWriteTask(collection, documents);
    }

    public static void deleteOne(String collection, Query query) {
        Map<String, Document> documents = getCachedDocuments(collection);
        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                documents.remove(existingDoc.getId());
                break;
            }
        }
        updateCache(collection, documents);
        executeWriteTask(collection, documents);
    }

    public static void deleteMany(String collection, Query query) {
        Map<String, Document> documents = getCachedDocuments(collection);
        documents.values().removeIf(query::matches);
        updateCache(collection, documents);
        executeWriteTask(collection, documents);
    }

    private static Map<String, Document> getCachedDocuments(String collection) {
        return collectionCache.computeIfAbsent(collection, k -> new ConcurrentHashMap<>());
    }

    private static void updateCache(String collection, Map<String, Document> documents) {
        collectionCache.put(collection, documents);
    }

    private static void executeWriteTask(String collection, Map<String, Document> documents) {
        executor.execute(() -> {
            String filePath = PATH + collection + ".json";
            JSONObject jsonObject = new JSONObject();
            documents.forEach((key, value) -> jsonObject.put(key, new JSONObject(value.toJSON())));
            writeJSONObjectToFile(jsonObject, filePath);
        });
    }
}