package database;

import repository.security.RolesRepository;
import repository.security.RolesRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;


public class Bootstrap {

    private static RolesRepository rolesRepository;

    public static void main(String[] args) throws SQLException {
        //dropAll();

        bootstrapTables();

        bootstrapUserData();
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role`;",
                    "DROP TABLE `role`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE `user_role`;",
                    "TRUNCATE `role`;",
                    "TRUNCATE `client`;",
                    "DROP TABLE `client`;",
                    "TRUNCATE `account`;",
                    "DROP TABLE `account`;",
                    "DROP TABLE `role`, `user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapUserData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rolesRepository = new RolesRepositoryMySQL(connectionWrapper.getConnection());

            bootstrapRoles();


            bootstrapUserRoles();
        }
    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rolesRepository.addRole(role);
        }
    }






    private static void bootstrapUserRoles() throws SQLException {

    }
}
