package server.services;

import lib.ResponseBody;
import server.Database;
import server.Operator;
import server.Player;
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
        Document doc = Database.findOne(Player.class.getName(), query);

        if (doc != null) {
            response.setOk(true);
            response.addField("user", doc.deJSONDocument(User.class));
            response.addField("isOperator", false);
            return response;
        }

        doc = Database.findOne(Operator.class.getName(), query);

        if (doc == null) {
            response.setOk(false);
            return response;
        }

        response.addField("user", doc.deJSONDocument(User.class));
        response.setOk(true);
        response.addField("isOperator", true);
        return response;
    }

    public ResponseBody signup(String name, String password) {
        ResponseBody response = new ResponseBody();

        Query query = new Query();
        query.addFilter("name", name);

        if (Database.findOne(Player.class.getName(), query) != null) {
            response.setOk(false);
            return response;
        }

        Player player = new Player(name, "jorgenicktales", password); // TODO! Make a proper nick function
        player.getDocument().saveToDatabase(Player.class);

        response.setOk(true);
        response.addField("user", player);
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
