package server;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.UserSchema;

import javax.print.Doc;

public class User implements JSONable {

    private String name, nick, password, id;

    private Boolean isOperator;

    public User() {}

    public User(String name, String nick, String password, Boolean isOperator) {
        this.name = name;
        this.nick = nick;
        this.password = password;
        this.isOperator = isOperator;
    }

    public User(Document document) {
        this.isOperator = false;
        //Document.setFieldsFromDocument(this, document);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Boolean getOperator() {
        return this.isOperator;
    }

    public void setOperator(Boolean operator) {
        this.isOperator = operator;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Document getDocument() {
        Document document = new Document(new UserSchema());
        if(id != null)
            document.setProperty("id", this.id);
        else
            this.setId(document.getId());
        document.setProperty("name", this.name);
        document.setProperty("nick", this.nick);
        document.setProperty("password", this.password);
        return document;
    }
}
