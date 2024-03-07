package server;

public class Database {

    public static Database instance;

    private User user;
    public Database ()
    {
        if(instance != null)
            instance = this;
    }

}
