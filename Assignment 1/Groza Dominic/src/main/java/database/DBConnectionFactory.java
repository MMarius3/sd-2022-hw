package database;

public class DBConnectionFactory {

    public JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper("front-desk");
        }
        return new JDBConnectionWrapper("test_front-desk");
    }
}