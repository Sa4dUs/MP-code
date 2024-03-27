package server;

import server.nosql.Document;

public class User {

    private String name, nick, password;

    private Boolean isOperator;

    public User() {}

    public User(String name, String nick, String password, Boolean isOperator) {
        this.name = name;
        this.nick = nick;
        this.password = password;
        this.isOperator = isOperator;
    }

    public User(Document doc) {
        this.name = (String) doc.getProperty("name");
        this.nick = (String) doc.getProperty("nick");
        this.password = (String) doc.getProperty("password");
        this.isOperator = doc.getProperty("isOperator").equals("true");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Boolean getOperator() {
        return isOperator;
    }

    public void setOperator(Boolean operator) {
        isOperator = operator;
    }
}
