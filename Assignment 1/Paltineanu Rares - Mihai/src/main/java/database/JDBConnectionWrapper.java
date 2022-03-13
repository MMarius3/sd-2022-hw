package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    private static final String USER = "root";
    private static final String PASS = "root";
    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBConnectionWrapper(String schema) {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASS);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() {
        try (Statement statement = connection.createStatement()) {
            String s = new SQLTableCreationFactory().getCreateSQLForTable("USER");
            statement.execute(s);
            statement.execute(new SQLTableCreationFactory().getCreateSQLForTable("CLIENT"));
            statement.execute(new SQLTableCreationFactory().getCreateSQLForTable("ROLE"));
            statement.execute(new SQLTableCreationFactory().getCreateSQLForTable("RIGHT"));
            statement.execute(new SQLTableCreationFactory().getCreateSQLForTable("ROLE_RIGHT"));
            statement.execute(new SQLTableCreationFactory().getCreateSQLForTable("USER_ROLE"));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}