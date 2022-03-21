package database;

import util.SQLSchemaUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    private static Connection connection;

    public static void setConnection(Connection connection) {
        DatabaseInitializer.connection = connection;
    }

    public static void dropDatabase(String databaseName) {
        loadSQL("DROP DATABASE " + databaseName);
    }

    public static void loadSchema(String databaseName) {
        try {
            ArrayList<String> createTablesQueries = SQLSchemaUtil.getCreateTablesQueries();
            createTablesQueries = (ArrayList<String>)
                    createTablesQueries
                            .stream()
                            .map(s -> s.replace("{DB}", databaseName))
                            .collect(Collectors.toList());

            createTablesQueries.forEach(DatabaseInitializer::loadSQL);

            ArrayList<String> constraintsQueries = SQLSchemaUtil.getConstraintsQueries();
            constraintsQueries.forEach(DatabaseInitializer::loadSQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void populateSchemaWithMockData() {
        try {
            ArrayList<String> createTables = SQLSchemaUtil.getMockDataQueries();
            createTables.forEach(DatabaseInitializer::loadSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadSQL(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void refreshDB() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(SQLSchemaUtil.getRefreshDBQuery());
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
