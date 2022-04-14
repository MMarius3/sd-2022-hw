package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnectionWrapper {


  private static final String JDBC_DRIVER = "org.postgresql.Driver";
  private static final String DB_URL = "jdbc:postgresql://localhost:5434/";

  private static final String USER = "sd_user";
  private static final String PASS = "cosma";
  private static final int TIMEOUT = 5;

  private Connection connection;

  public JDBConnectionWrapper(String schema) {
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL + schema, USER, PASS);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public boolean testConnection() throws SQLException {
    return connection.isValid(TIMEOUT);
  }

  public Connection getConnection() {
    return connection;
  }

}
