package repository.user;

import controller.Response;
import model.Account;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import service.user.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

  private final Connection connection;
  private final RightsRolesRepository rightsRolesRepository;


  public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
    this.connection = connection;
    this.rightsRolesRepository = rightsRolesRepository;
  }

  @Override
  public List<User> findAllWithRole(Role role) {
    rightsRolesRepository.findRoleByTitle(role.getRole());
    List<User> users = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
//      String sql = "Select distinct user.id, username, password from user inner join user_role on role_id=" + role.getId() + " where role_id=" + role.getId();
      String sql = "Select * from user";
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        users.add(getUserFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users.stream()
            .filter(x -> x.getRoles().stream()
                    .anyMatch(y -> y.getRole().equals(role.getRole())))
            .collect(Collectors.toList());
  }

  @Override
  public User findById(Long id) {
    try {
      PreparedStatement statement = connection
              .prepareStatement("Select * from user where id = ?");
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()){
        return getUserFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public User findByUsernameAndPassword(String username, String password) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
          "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      userResultSet.next();
      return getUserFromResultSet(userResultSet);
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
    return null;
  }

  @Override
  public boolean save(User user) {
    try {
      PreparedStatement insertUserStatement = connection
          .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      insertUserStatement.setString(1, user.getUsername());
      insertUserStatement.setString(2, user.getPassword());
      insertUserStatement.executeUpdate();

      ResultSet rs = insertUserStatement.getGeneratedKeys();
      rs.next();
      long userId = rs.getLong(1);
      user.setId(userId);

      rightsRolesRepository.addRolesToUser(user, user.getRoles());

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public boolean updateById(Long id, User user) {
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");

      insertStatement.setString(1, user.getUsername());
      insertStatement.setString(2, PasswordEncoder.encode(user.getPassword()));
      insertStatement.setLong(3, id);
      insertStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean removeById(Long id) {
    try {
      PreparedStatement statement = connection
              .prepareStatement("DELETE from user WHERE id=?");

      statement.setLong(1, id);
      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public void removeAll() {
    try {
      Statement statement = connection.createStatement();
      String sql = "DELETE from user where id >= 0";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Response<Boolean> existsByUsername(String email) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
          "Select * from `" + USER + "` where `username`=\'" + email + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      return new Response<>(userResultSet.next());
    } catch (SQLException e) {
      return new Response<>(singletonList(e.getMessage()));
    }
  }

  private User getUserFromResultSet(ResultSet rs) throws SQLException {
    return new UserBuilder()
            .setId(rs.getLong("id"))
            .setUsername(rs.getString("username"))
            .setPassword(rs.getString("password"))
            .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
            .build();
  }
}
