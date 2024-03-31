package server;

import server.nosql.Document;

public class Operator extends User{
    public Operator(){}
    public Operator(String name, String password) {
        super(name, password);
    }

    @Override
    public Document getDocument() {
        return super.getDocument();
    }

}
