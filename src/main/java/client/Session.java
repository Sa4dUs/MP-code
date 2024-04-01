package client;

import server.Operator;
import server.User;

public class Session {

    private static User currentUser;
    private static Boolean isOperator = false;

    public Session() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static Boolean isOperator() {
        return isOperator;
    }

    public static void sudo() {
        isOperator = true;
    }
}
