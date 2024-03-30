package server;

import server.nosql.Document;

public class Operator extends User{
    public Operator(){}
    public Operator(String name, String nick, String password) {
        super(name, nick, password);
    }

    @Override
    public Document getDocument() {
        return super.getDocument();
    }

}
