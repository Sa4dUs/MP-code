package server.services;

import lib.ResponseBody;
import server.Database;
import server.nosql.Collection;
import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schemas.UserSchema;

public class AuthenticationService implements Service {
    public ResponseBody login(String username, String password) {
        Query query = new Query();
        query.addFilter("username", username);
        query.addFilter("password", password);
        Document user = Database.findOne(Collection.USER, query);
        return new ResponseBody( user != null );
    }

    public ResponseBody signup(String username, String password) {
        Query query = new Query();
        query.addFilter("username", username);

        if (Database.findOne(Collection.USER, query) != null) {
            return new ResponseBody(false);
        }

        Document doc = new Document(new UserSchema());
        doc.setProperty("username", username);
        doc.setProperty("password", password);
        Database.insertOne(Collection.USER, doc);

        return new ResponseBody(true);
    }
}
