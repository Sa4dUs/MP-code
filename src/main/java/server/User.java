package server;

import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.UserSchema;

import javax.print.Doc;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements JSONable {

    private String name, nick, password, id;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.nick = this.genNick();
        this.password = Crypto.encrypt(password);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String genNick() {
        StringBuilder output = new StringBuilder();

        int hash = this.name.hashCode();

        output.append((char) (65 + ((int) this.name.charAt(0))%26)); // Add L
        output.append(10 + Math.abs(hash%90)); // Add NN
        output.append((char) (65 + Math.abs(hash%26))); // Add LL
        output.append((char) (65 + Math.abs((2147483647*hash)%26))); // Add LL

        return output.toString();
    }

    public String getNick() {
        return this.nick;
    }

    public String getId() {
        return this.id != null ? this.id : this.nick;
    }

    public void setId(String id) {
        this.id = id;
        this.nick = id;
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
