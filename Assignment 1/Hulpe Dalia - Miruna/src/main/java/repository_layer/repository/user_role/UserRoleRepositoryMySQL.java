package repository_layer.repository.user_role;

import bussiness_layer.models.Role;
import bussiness_layer.models.User;
import repository_layer.repository.role.RoleRepositoryMySQL;
import repository_layer.repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static repository_layer.database_builder.Constants.Tables.USER_ROLE;

public class UserRoleRepositoryMySQL implements UserRoleRepository {

  private final Connection connection;
  private final UserRepositoryMySQL userRepositoryMySQL;
  private final RoleRepositoryMySQL roleRepositoryMySQL;

  public UserRoleRepositoryMySQL(Connection connection)

  {
    this.connection = connection;
    this.userRepositoryMySQL = new UserRepositoryMySQL(connection);
    this.roleRepositoryMySQL = new RoleRepositoryMySQL(connection);
  }

  @Override
  public boolean insert(String role, User user) {
    Role roleEntity = roleRepositoryMySQL.findByName(role);
    Long userId = userRepositoryMySQL.insert(user);
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("INSERT INTO " + USER_ROLE + " values (null, ?, ?)");
      insertStatement.setLong(1, userId);
      insertStatement.setLong(2, roleEntity.getId());
      insertStatement.executeUpdate();


      return true;
    } catch (SQLException e) {
    }
    return false;
  }

  @Override
  public User getUserWithRoles(String username, String password) {
    User user = userRepositoryMySQL.findByUsernameAndPassword(username, password);
    if (user != null)
    {
      try {
        PreparedStatement findStatement = connection
                .prepareStatement("SELECT * FROM " + USER_ROLE + " WHERE `user_id` = "+ user.getId());

        ResultSet roleResultSet = findStatement.executeQuery();

        List<Role> roles = new ArrayList<>();

        while (roleResultSet.next())
        {
          Role role = roleRepositoryMySQL.findById(roleResultSet.getLong("role_id"));
          roles.add(role);
        }

        user.setRoles(roles);

        return user;
      } catch (SQLException e) {
      }
      return null;
    }
    return null;
  }

  @Override
  public User getUserWithRolesUsername(String username) {
    User user = userRepositoryMySQL.findByUsername(username);
    if (user != null)
    {
      try {
        PreparedStatement findStatement = connection
                .prepareStatement("SELECT * FROM " + USER_ROLE + " WHERE `user_id` = "+ user.getId());

        ResultSet roleResultSet = findStatement.executeQuery();

        List<Role> roles = new ArrayList<>();

        while (roleResultSet.next())
        {
          Role role = roleRepositoryMySQL.findById(roleResultSet.getLong("role_id"));
          roles.add(role);
        }

        user.setRoles(roles);

        return user;
      } catch (SQLException e) {
      }
      return null;
    }
    return null;
  }

  @Override
  public List<User> getUsersByRole(String roleName) {
    Role role = roleRepositoryMySQL.findByName(roleName);
    if (role != null)
    {
      try {
        PreparedStatement findStatement = connection
                .prepareStatement("SELECT * FROM " + USER_ROLE + " WHERE `role_id` = "+ role.getId());

        ResultSet roleResultSet = findStatement.executeQuery();

        List<User> users = new ArrayList<>();

        while (roleResultSet.next())
        {
          User user = userRepositoryMySQL.findById(roleResultSet.getLong("user_id"));
          users.add(user);
        }
        return users;
      } catch (SQLException e) {
      }
      return null;
    }
    return null;
  }
}
