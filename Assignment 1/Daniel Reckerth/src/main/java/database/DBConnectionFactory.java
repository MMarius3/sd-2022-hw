package database;

public class DBConnectionFactory {
  public static final String TEST = "test_bank";
  public static final String PRODUCTION = "bank";

  public static JDBConnectionWrapper getConnectionWrapper(boolean test) {
    if(test) {
      return new JDBConnectionWrapper(TEST);
    } else {
      return new JDBConnectionWrapper(PRODUCTION);
    }
  }
}
