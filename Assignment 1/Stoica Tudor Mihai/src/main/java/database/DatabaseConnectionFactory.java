package database;

import java.sql.Connection;

public class DatabaseConnectionFactory {

    private static final String TEST_SCHEMA = "BankManagementTest";
    private static final String SCHEMA = "BankManagement";

    public static Connection getProductionConnection() {
        return getConnection(false);
    }

    public static Connection getConnection(boolean test) {

        String schema = SCHEMA;

        if (test) {
            schema = TEST_SCHEMA;
        }

        try {
            if (!JDBConnectionWrapper.testConnection(schema)) {
                JDBConnectionWrapper.setConnection(schema);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return JDBConnectionWrapper.getConnection();
    }
}
