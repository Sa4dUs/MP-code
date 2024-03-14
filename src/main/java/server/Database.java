package server;

import org.json.JSONArray;
import org.json.JSONObject;
import server.nosql.Document;
import server.nosql.Query;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Database {

    public static Database instance;
    private static final String PATH = "./src/main/resources/";

    public Database() {
        if (instance != null)
            instance = this;
    }

    public static Document findOne(String collection, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);

            if (jsonObject.has(collection)) {
                JSONArray jsonArray = jsonObject.getJSONArray(collection);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Document existingDoc = new Document(jsonArray.getJSONObject(i));
                    if (query.matches(existingDoc)) {
                        return existingDoc;
                    }
                }
            } else {
                System.out.println("Collection not found: " + collection);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return null;
    }

    public static List<Document> findMany(String collection, Query query) {
        List<Document> result = new ArrayList<>();

        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);

            if (jsonObject.has(collection)) {
                JSONArray jsonArray = jsonObject.getJSONArray(collection);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Document existingDoc = new Document(jsonArray.getJSONObject(i));
                    if (query.matches(existingDoc)) {
                        result.add(existingDoc);
                    }
                }
            } else {
                System.out.println("Collection not found: " + collection);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return result;
    }

    public static void insertOne(String collection, Document document) {
        String json = document.toJSON();
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);

            JSONArray jsonArray;
            if (jsonObject.has(collection)) {
                jsonArray = jsonObject.getJSONArray(collection);
            } else {
                jsonArray = new JSONArray();
            }
            jsonArray.put(new JSONObject(json));
            jsonObject.put(collection, jsonArray);

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(jsonObject.toString(4));
                System.out.println("Document inserted into file: " + filePath);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void insertMany(String collection, List<Document> documents) {
        for (Document doc: documents) {
            insertOne(collection, doc);
        }
    }

    public static void updateOne(String collection, Document document, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);

            if (jsonObject.has(collection)) {
                JSONArray jsonArray = jsonObject.getJSONArray(collection);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Document existingDoc = new Document(jsonArray.getJSONObject(i));
                    if (query.matches(existingDoc)) {
                        existingDoc.updateFromDocument(document);
                        jsonArray.put(i, new JSONObject(existingDoc.toJSON()));
                    }
                }
                jsonObject.put(collection, jsonArray);

                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(jsonObject.toString(4));
                    System.out.println("Document updated in file: " + filePath);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
            } else {
                System.out.println("Collection not found: " + collection);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void deleteOne(String collection, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);

            if (jsonObject.has(collection)) {
                JSONArray jsonArray = jsonObject.getJSONArray(collection);

                for (int i = 0; i < jsonArray.length(); i++) {
                    Document existingDoc = new Document(jsonArray.getJSONObject(i));
                    if (query.matches(existingDoc)) {
                        jsonArray.remove(i);
                        break;
                    }
                }
                jsonObject.put(collection, jsonArray);

                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(jsonObject.toString(4));
                    System.out.println("Document deleted from file: " + filePath);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
            } else {
                System.out.println("Collection not found: " + collection);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void deleteMany(String collection, Query query) {
        String filePath = PATH + collection + ".json";

        try {
            JSONObject jsonObject = readJSONObjectFromFile(filePath);

            if (jsonObject.has(collection)) {
                JSONArray jsonArray = jsonObject.getJSONArray(collection);
                JSONArray updatedArray = new JSONArray();

                for (int i = 0; i < jsonArray.length(); i++) {
                    Document existingDoc = new Document(jsonArray.getJSONObject(i));
                    if (!query.matches(existingDoc)) {
                        updatedArray.put(jsonArray.getJSONObject(i));
                    }
                }
                jsonObject.put(collection, updatedArray);

                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(jsonObject.toString(4));
                    System.out.println("Documents deleted from file: " + filePath);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
            } else {
                System.out.println("Collection not found: " + collection);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static JSONObject readJSONObjectFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        JSONObject jsonObject;
        if (file.exists()) {
            StringBuilder existingContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    existingContent.append(line).append("\n");
                }
            }
            jsonObject = new JSONObject(existingContent.toString());
        } else {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }
}
