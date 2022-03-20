package repository.user;

import controller.Response;
import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
  public User findByUsernameAndPassword(String username, String password) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
          "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      userResultSet.next();

      User user = new UserBuilder()
          .setUsername(userResultSet.getString("username"))
          .setPassword(userResultSet.getString("password"))
          .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))    //TODO rights
          .build();

      return user;
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
    return null;
  }

  @Override
  public List<User> findAll() {
    List<User> users = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from user";
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        users.add(getUsersFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

  @Override
  public Optional<User> findById(Long id) {
    User user = null;
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from user where id=" + id;
      ResultSet rs = statement.executeQuery(sql);

      user = getUsersFromResultSet(rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> findByName(String name) {
    return Optional.empty();
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

  private User getUsersFromResultSet(ResultSet rs) throws SQLException {
    return new  UserBuilder()
            .setUsername(rs.getString("username"))
            .setPassword(rs.getString("password"))
            .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
            .build();
  }

}
