package server.services;

import lib.ResponseBody;
import server.Database;
import server.nosql.Document;
import server.nosql.JSONable;

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

    public ResponseBody setItem(JSONable object)
    {
        Document document = object.getDocument();
        System.out.println(object.getClass().getName());
        document.saveToDatabase(object.getClass());
        return new ResponseBody(true);
    }
}
