package server.services;

import lib.ResponseBody;
import server.Database;
import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Query;

import java.util.ArrayList;
import java.util.List;

public class ItemService implements Service{

    public ResponseBody getItem(String id, Class<?> clazz)
    {
        Document document = Document.getDocument(id, clazz);
        if(document == null)
            return new ResponseBody(false);

        ResponseBody responseBody = new ResponseBody(true);
        responseBody.addField("data", document.deJSONDocument(clazz));
        return  responseBody;
    }

    public ResponseBody getAll(Class<?> clazz)
    {
        List<Document> documents = Database.findMany(clazz.getName(), new Query());
        List<Object> res = new ArrayList<>();
        for (Document document: documents)
            res.add(document.deJSONDocument(clazz));

        ResponseBody responseBody = new ResponseBody(true);
        responseBody.addField("data", res);
        return responseBody;
    }

    public ResponseBody setItem(JSONable object)
    {
        Document document = object.getDocument();
        System.out.println(object.getClass().getName());
        document.saveToDatabase(object.getClass());
        return new ResponseBody(true);
    }

    public ResponseBody deleteItem(JSONable item) {
        Query query = new Query();
        query.addFilter("id", item.getDocument().getId());

        Database.deleteMany(item.getClass().getName(), query);
        return new ResponseBody(true);
    }
}
