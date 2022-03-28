package database;

//import repository.security.RightsRolesRepository;
//import repository.security.RightsRolesRepositoryMySQL;

import repository.security.RoleRepositoryMySQL;
import repository.security.RolesRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;


public class Boostraper {

    private RolesRepository rolesRepository;

    public void execute() throws SQLException{


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
                    "TRUNCATE `client_account`;",
                    "DROP TABLE `client_account`;",
                    "TRUNCATE `account`;",
                    "DROP TABLE `account`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE `user_role`;",
                    "TRUNCATE `role`;",
                    "TRUNCATE `user`;",
                    "TRUNCATE `action`",
                    "DROP TABLE `action`",
                    "DROP TABLE  `client`, `role`, `user`;"
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

  private void bootstrapUserData() {
    for (String schema : SCHEMAS) {
      System.out.println("Bootstrapping user data for " + schema);

      JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
        rolesRepository = new RoleRepositoryMySQL(connectionWrapper.getConnection());

      bootstrapRoles();
      bootstrapUserRoles();
    }
  }

  private void bootstrapRoles() {
    for (String role : ROLES) {
        rolesRepository.addRole(role);
    }
  }

  private void bootstrapUserRoles() {

  }
}
