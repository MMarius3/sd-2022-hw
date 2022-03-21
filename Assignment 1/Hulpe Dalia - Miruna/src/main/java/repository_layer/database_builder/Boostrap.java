package repository_layer.database_builder;

import bussiness_layer.builder.RoleBuilder;
import bussiness_layer.models.Role;
import repository_layer.repository.account.AccountRepository;
import repository_layer.repository.account.AccountRepositoryMySQL;
import repository_layer.repository.role.RoleRepository;
import repository_layer.repository.role.RoleRepositoryMySQL;
import repository_layer.repository.user_role.UserRoleRepository;
import repository_layer.repository.user_role.UserRoleRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Boostrap {

  private static RoleRepository roleRepository;
  private static UserRoleRepository userRoleRepository;
  private static AccountRepository accountRepository;

  public static void main(String[] args) throws SQLException {
    dropAll();

    bootstrapTables();

    bootstrapData();
  }

  private static void dropAll() throws SQLException {
    for (String schema : Constants.Schemas.SCHEMAS) {
      System.out.println("Dropping all tables in schema: " + schema);

      Connection connection = new JDBConnectionWrapper(schema).getConnection();
      Statement statement = connection.createStatement();

      String[] dropStatements = {
              "TRUNCATE `account`;",
              "DROP TABLE `account`;",
              "TRUNCATE `action`;",
              "DROP TABLE `action`;",
              "TRUNCATE `user_role`;",
              "DROP TABLE `user_role`;",
              "TRUNCATE `role`;",
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

    for (String schema : Constants.Schemas.SCHEMAS) {
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

  private static void bootstrapData() throws SQLException {
    for (String schema : Constants.Schemas.SCHEMAS) {
      System.out.println("Bootstrapping user data for " + schema);

      JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);

        roleRepository = new RoleRepositoryMySQL(connectionWrapper.getConnection());
        userRoleRepository = new UserRoleRepositoryMySQL(connectionWrapper.getConnection());
        accountRepository = new AccountRepositoryMySQL(connectionWrapper.getConnection());
        bootstrapRoles();
        bootstrapUserRoles();
        bootstrapAccounts();
    }
  }

  private static void bootstrapRoles() throws SQLException {
    for (String role : Constants.Roles.ROLES) {
      Role roleEntity = new RoleBuilder()
              .setRole(role)
              .build();
      roleRepository.insert(roleEntity);
    }
  }

  private static void bootstrapUserRoles() throws SQLException {
    userRoleRepository.insert(Constants.Roles.ADMINISTRATOR, Constants.Users.ADMIN_USER);
    userRoleRepository.insert(Constants.Roles.EMPLOYEE, Constants.Users.EMPLOYEE_USER);
    userRoleRepository.insert(Constants.Roles.CUSTOMER, Constants.Users.CUSTOMER_USER);
  }

  private static void bootstrapAccounts() throws SQLException {
    accountRepository.insert(Constants.Accounts.LEI, Constants.Users.CUSTOMER_USER.getUsername());
    accountRepository.insert(Constants.Accounts.EURO, Constants.Users.CUSTOMER_USER.getUsername());
  }
}
