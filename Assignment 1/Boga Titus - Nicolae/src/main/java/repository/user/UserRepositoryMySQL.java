package repository.user;

import model.Account;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

  private final Connection connection;
  private final RightsRolesRepository rightsRolesRepository;


  public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
    this.connection = connection;
    this.rightsRolesRepository = rightsRolesRepository;
  }

  @Override
  public List<User> findAll() {
    List<User> users = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from user";
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        users.add(getAccountFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

  @Override
  public Notification<User> findByUsernameAndPassword(String username, String password) {
    Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
    try {
      Statement statement = connection.createStatement();
      String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      if (userResultSet.next()) {
        User user = new UserBuilder()
            .setUsername(userResultSet.getString("username"))
            .setPassword(userResultSet.getString("password"))
            .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
            .build();
        findByUsernameAndPasswordNotification.setResult(user);
        return findByUsernameAndPasswordNotification;
      } else {
        findByUsernameAndPasswordNotification.addError("Invalid email or password!");
        return findByUsernameAndPasswordNotification;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
    }
    return findByUsernameAndPasswordNotification;
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
  public Optional<User> findUserById(Long id) {
    Optional<User> user = null;
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from user where id =" + id;
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {

        user= Optional.ofNullable(getAccountFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
    return user;
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
  public void updateEmail(User user) {
    try {

      PreparedStatement insertStatement = connection
              .prepareStatement("UPDATE user SET username=? where id =?");
      insertStatement.setString(1, user.getUsername());
      insertStatement.setInt(2, Math.toIntExact(user.getId()));
      insertStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();

    }
  }

  private User getAccountFromResultSet(ResultSet rs) throws  SQLException{
    User user = new User();
    user.setId(rs.getLong("id"));
    user.setUsername(rs.getString("username"));

    return user;
  }

}
