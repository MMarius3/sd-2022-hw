package repository.security;

import database.enums.RightType;
import database.enums.RoleType;
import model.Right;
import model.Role;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.enums.TableTypeSQL.*;

public class RightsRolesRepositoryMySQL implements RightsRolesRepository {

  private final Connection connection;

  public RightsRolesRepositoryMySQL(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void addRole(String role) {
    try {
      String sql = "INSERT IGNORE INTO %s values (null, ?)".formatted(ROLE.getLabel());
      PreparedStatement insertStatement = connection.prepareStatement(sql);
      insertStatement.setString(1, role);
      insertStatement.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Override
  public void addRight(String right) {
    try {
      String sql = "INSERT IGNORE INTO %s values (null, ?)".formatted(RIGHT.getLabel());
      PreparedStatement insertStatement = connection.prepareStatement(sql);
      insertStatement.setString(1, right);
      insertStatement.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Override
  public Optional<Role> findRoleByTitle(String role) {
    Statement statement;
    try {
      statement = connection.createStatement();
      String fetchRoleSql = "SELECT * FROM %s where `role`='%s'".formatted(ROLE.getLabel(), role);
      ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
      roleResultSet.next();
      Long roleId = roleResultSet.getLong("id");
      String roleTitle = roleResultSet.getString("role");
      return Optional.of(new Role(roleId, RoleType.valueOfLabel(roleTitle), null));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public Optional<Role> findRoleById(Long id) {
    Statement statement;
    try {
      statement = connection.createStatement();
      String fetchRoleSql = "Select * FROM %s where `id`='%d'".formatted(ROLE.getLabel(), id);
      ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
      roleResultSet.next();
      String roleTitle = roleResultSet.getString("role");
      return Optional.of(new Role(id, RoleType.valueOfLabel(roleTitle), null));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public Optional<Right> findRightByTitle(String right) {
    Statement statement;
    try {
      statement = connection.createStatement();
      String fetchRoleSql = "Select * FROM %s where name='%s'".formatted(RIGHT.getLabel(), right);
      ResultSet rightResultSet = statement.executeQuery(fetchRoleSql);
      rightResultSet.next();
      Long rightId = rightResultSet.getLong("id");
      String rightTitle = rightResultSet.getString("name");
      return Optional.of(new Right(rightId, RightType.valueOfLabel(rightTitle)));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public Optional<Right> findRightById(Long id) {
    Statement statement;
    try {
      statement = connection.createStatement();
      String fetchRoleSql = "Select * FROM %s where `id`='%d'".formatted(RIGHT.getLabel(), id);
      ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
      roleResultSet.next();
      String rightTitle = roleResultSet.getString("name");
      return Optional.of(new Right(id, RightType.valueOfLabel(rightTitle)));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public void addRolesToUsers(User user, List<Role> roles) {
    try {
      for (Role role : roles) {
        PreparedStatement insertUserRoleStatement = connection
                .prepareStatement("INSERT INTO %s values (null, ?, ?)".formatted(USER_ROLE.getLabel()));
        insertUserRoleStatement.setLong(1, user.getId());
        insertUserRoleStatement.setLong(2, role.getId());
        insertUserRoleStatement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Role> findRolesForUsers(Long id) {
    try {
      List<Role> roles = new ArrayList<>();
      Statement statement = connection.createStatement();
      String fetchRoleSql = "Select * from %s where `user_id`='%d'".formatted(USER_ROLE.getLabel(), id);
      ResultSet userRoleResultSet = statement.executeQuery(fetchRoleSql);
      while (userRoleResultSet.next()) {
        long roleId = userRoleResultSet.getLong("role_id");
        Optional<Role> roleOptional = findRoleById(roleId);
        roleOptional.ifPresent(roles::add);
      }
      return roles;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void addRoleRight(Long roleId, Long rightId) {
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("INSERT IGNORE INTO %s values (null, ?, ?)".formatted(ROLE_RIGHT.getLabel()));
      insertStatement.setLong(1, roleId);
      insertStatement.setLong(2, rightId);
      insertStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
