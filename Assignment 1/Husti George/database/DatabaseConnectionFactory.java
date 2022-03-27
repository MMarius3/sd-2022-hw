package database;

public class DatabaseConnectionFactory {

    private static final String TEST_SCHEMA = "BankTest";
    private static final String SCHEMA = "Bank";

    public static JDBCConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBCConnectionWrapper(TEST_SCHEMA, true);
        }
        return new JDBCConnectionWrapper(SCHEMA, false);
    }

}