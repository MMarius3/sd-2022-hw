package database;

public class DBConnectionFactory {

    public static final String PRODUCTION = "bank app";
    public static final String TEST = "bank app test";

    public JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper(TEST);
        }
        return new JDBConnectionWrapper(PRODUCTION);
    }
}
