package lib;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class JSON {
    public static JSONObject readJSONObjectFromFile(String filePath) throws IOException {
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

            if (existingContent.compareTo(new StringBuilder()) == 0) {
                existingContent = new StringBuilder("{}");
            }

            jsonObject = new JSONObject(existingContent.toString());
        } else {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    public static void writeJSONObjectToFile(JSONObject jsonObject, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonObject.toString(4));
            System.out.println("Operation successful. File path: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static JSONArray getJSONArrayFromJSONObject(JSONObject jsonObject, String collection) {
        JSONArray jsonArray;
        if (jsonObject.has(collection)) {
            jsonArray = jsonObject.getJSONArray(collection);
        } else {
            jsonArray = new JSONArray();
        }
        return jsonArray;
    }
}
