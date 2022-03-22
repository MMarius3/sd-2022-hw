package database;

import database.enums.RightType;
import database.enums.RoleType;
import database.enums.TableTypeSQL;
import model.Right;
import model.Role;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static database.Constants.*;

public class Bootstrapper {
  private RightsRolesRepository rightsRolesRepository;

  public void execute() throws SQLException {
    dropAll();
    bootstrapTables();
    bootstrapUserData();
  }

  private void dropAll() throws SQLException {
    for(String schema : SCHEMAS) {
      System.out.println("Dropping all tables in schema: " + schema + "...");
      Connection connection = new JDBConnectionWrapper(schema).getConnection();
      Statement statement = connection.createStatement();

      String[] dropStatements = {
              "TRUNCATE `role_right`;",
              "DROP TABLE `role_right`;",
              "TRUNCATE `activity`;",
              "DROP TABLE `activity`;",
              "TRUNCATE `right`;",
              "DROP TABLE `right`;",
              "TRUNCATE `user_role`;",
              "DROP TABLE `user_role`;",
              "TRUNCATE `account`;",
              "DROP TABLE `account`",
              "TRUNCATE `role`;",
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

    System.out.println("\tDone dropping table bootstrap.");
  }

  private static void bootstrapTables() throws SQLException {
    SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

    for(String schema : SCHEMAS) {
      System.out.println("Bootstrapping " + schema + " schema...");
      JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
      Connection connection = connectionWrapper.getConnection();
      Statement statement = connection.createStatement();

      for(TableTypeSQL tableTypeSQL : ORDERED_TABLES_FOR_CREATION) {
        String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(tableTypeSQL);
        statement.execute(createTableSQL);
      }
    }
    System.out.println("\tDone table bootstrap.");
  }

  private void bootstrapUserData() {
    for(String schema : SCHEMAS) {
      System.out.println("Bootstrapping user data for " + schema + "...");

      JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
      this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());

      bootstrapRoles();
      bootstrapRights();
      bootstrapRoleRights();
    }
  }

  private void bootstrapRoles() {
    for(RoleType roleType : ROLE_TYPES) {
      rightsRolesRepository.addRole(roleType.getLabel());
    }
    System.out.println("\tDone roles bootstrap.");
  }

  private void bootstrapRights() {
    for(RightType rightType : RIGHT_TYPES) {
      rightsRolesRepository.addRight(rightType.getLabel());
    }
    System.out.println("\tDone rights bootstrap.");
  }


  private void bootstrapRoleRights() {
    Map<RoleType, List<RightType>> rolesRights = Constants.getRoleRights();

    for(RoleType roleType : rolesRights.keySet()) {
      Role role = rightsRolesRepository.findRoleByTitle(roleType.getLabel())
              .orElseThrow(() -> new IllegalArgumentException("No role of type " + roleType.getLabel()));
      Long roleId = role.getId();

      for (RightType rightType : rolesRights.get(roleType)) {
        Right right = rightsRolesRepository.findRightByTitle(rightType.getLabel())
                .orElseThrow(() -> new IllegalArgumentException("No right of type " + rightType.getLabel()));
        Long rightId = right.getId();
        rightsRolesRepository.addRoleRight(roleId, rightId);
      }
    }
    System.out.println("\tDone role->rights bootstrap.");
  }
}
