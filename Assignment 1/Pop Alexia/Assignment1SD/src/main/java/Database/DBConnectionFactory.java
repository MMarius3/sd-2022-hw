package Database;

public class DBConnectionFactory {

    public JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if(test) return new JDBConnectionWrapper(Constants.Schemas.TEST);
        else return new JDBConnectionWrapper(Constants.Schemas.PRODUCTION);
    }
}