package database;

public class DBConnectionFactory {
    private static final String TEST_SCHEMA = "test_bank";
    private static final String SCHEMA = "bank";

    public static JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper(TEST_SCHEMA);
        }
        return new JDBConnectionWrapper(SCHEMA);
    }
}
