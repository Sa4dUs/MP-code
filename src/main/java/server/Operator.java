package server;

import server.nosql.Document;

public class Operator extends User{
    public Operator(String name, String nick, String password, Boolean isOperator) {
        super(name, nick, password, isOperator);
    }

    public Operator(Document document)
    {
        super(document);
        Document.setFieldsFromDocument(this,document);
    }
}
