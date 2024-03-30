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
        Document documentToRemove = null;

        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                documentToRemove = existingDoc;
                break;
            }
        }

        if (documentToRemove != null) {
            documents.remove(documentToRemove.getId());
        } else {
            JSONObject jsonObject;
            try {
                jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
                for (String key : jsonObject.keySet()) {
                    JSONObject docObject = jsonObject.getJSONObject(key);
                    Document document = Document.fromJSON(docObject.toString());
                    if (query.matches(document)) {
                        jsonObject.remove(key);
                        break;
                    }
                }
                writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
            } catch (IOException ignored) {
            }
        }

        updateCache(collection, documents);
        executeWriteTask(collection, documents);
    }

    public static void deleteMany(String collection, Query query) {
        Map<String, Document> documents = getCachedDocuments(collection);

        // Track documents to remove directly from file
        List<String> docIdsToRemove = new ArrayList<>();

        for (Document existingDoc : documents.values()) {
            if (query.matches(existingDoc)) {
                docIdsToRemove.add(existingDoc.getId());
            }
        }

        if (docIdsToRemove.isEmpty()) {
            // Remove directly from file
            JSONObject jsonObject;
            try {
                jsonObject = readJSONObjectFromFile(PATH + collection + ".json");
                for (String key : jsonObject.keySet()) {
                    JSONObject docObject = jsonObject.getJSONObject(key);
                    Document document = Document.fromJSON(docObject.toString());
                    if (query.matches(document)) {
                        jsonObject.remove(key);
                    }
                }
                writeJSONObjectToFile(jsonObject, PATH + collection + ".json");
            } catch (IOException ignored) {
            }
        } else {
            // Remove from cache
            documents.values().removeIf(doc -> docIdsToRemove.contains(doc.getId()));
        }

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
            JSONObject jsonObject;
            try {
                String filePath = PATH + collection + ".json";
                jsonObject = readJSONObjectFromFile(filePath);
                JSONObject finalJsonObject = jsonObject;
                documents.forEach((key, value) -> finalJsonObject.put(key, new JSONObject(value.toJSON())));
                writeJSONObjectToFile(jsonObject, filePath);
            } catch (IOException ignored) {
            }
        });
    }
}