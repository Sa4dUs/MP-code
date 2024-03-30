package server;

import server.nosql.Document;

public class User {

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
}
