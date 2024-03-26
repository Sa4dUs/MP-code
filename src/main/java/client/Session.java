package client;

import server.User;

public class Session {

    private static User currentUser;

    public Session() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
