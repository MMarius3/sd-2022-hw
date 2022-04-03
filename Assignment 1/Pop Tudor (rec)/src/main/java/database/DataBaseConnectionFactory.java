package database;



public class DataBaseConnectionFactory {

    private static final String TEST_SCHEMA = "test_orderdb";
    private static final String SCHEMA = "orderdb";

    public static JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper(TEST_SCHEMA);
        }
        return new JDBConnectionWrapper(SCHEMA);
    }

}
