package server.services;

import client.Session;
import lib.ResponseBody;
import server.Database;
import server.User;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.UserSchema;

public class AuthenticationService implements Service {
    public ResponseBody login(String name, String password) {
        ResponseBody response = new ResponseBody();

        Query query = new Query();
        query.addFilter("name", name);
        query.addFilter("password", password);
        Document user = Database.findOne(Collection.USER, query);

        if (user == null) {
            response.setOk(false);
            return response;
        }

        response.setOk(true);
        response.addField("user", new User(user));
        return response;
    }

    public ResponseBody signup(String name, String password) {
        ResponseBody response = new ResponseBody();

        Query query = new Query();
        query.addFilter("name", name);

        if (Database.findOne(Collection.USER, query) != null) {
            response.setOk(false);
            return response;
        }

        Document doc = new Document(new UserSchema());
        doc.setProperty("name", name);
        doc.setProperty("password", password);
        doc.setProperty("isOperator", "false");
        doc.setProperty("nick", ""); // TODO! make a propper nick function
        Database.insertOne(Collection.USER, doc);

        response.setOk(true);
        response.addField("user", new User(doc));
        return response;
    }

    public ResponseBody delete(String id) {
        ResponseBody response = new ResponseBody();

        Query query = new Query();
        query.addFilter("id", id);
        Database.deleteOne(Collection.USER, query);

        response.setOk(true);

        return response;
    }
}
