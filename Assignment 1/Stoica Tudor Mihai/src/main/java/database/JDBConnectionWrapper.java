package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnectionWrapper {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    private static final String USER = "Citadin2";
    private static final String PASSWORD = "Aaladin2000-";
    private static final int TIMEOUT = 5;

    private static Connection connection;

    public static void setConnection(String schema) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL + schema, USER, PASSWORD);
    }

    public static boolean testConnection(String schema) throws SQLException {
        try {
            return (connection.isValid(TIMEOUT) && connection.getSchema().equals(schema));
        } catch (Exception ex) {
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
