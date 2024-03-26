package server;

import server.nosql.Document;

public abstract class User {

    private String name, nick, password;

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

    public User() {}

    public User(String name, String nick, String password) {
        this.name = name;
        this.nick = nick;
        this.password = password;
    }

    public User(Document doc) {
        this.name = (String) doc.getProperty("name");
        this.nick = (String) doc.getProperty("nick");
        this.password = (String) doc.getProperty("password");
    }
}
