package repository.user;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.CLIENT;
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
    return null;
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
        //System.out.println(user.getUsername()+"  "+user.getPassword());
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
  public Optional<User> findByUsername(String username) {
    try {
      Statement statement = connection.createStatement();
      String sql = "Select * from `" + USER +"` where `username`=\'" + username + "\'";
      ResultSet rs = statement.executeQuery(sql);

      if(rs.next()){
        return Optional.of(getUserFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public boolean create(User user) {
    try{
      PreparedStatement insertUserStatement = connection
              .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      insertUserStatement.setString(1, user.getUsername());
      insertUserStatement.setString(2, user.getPassword());
      insertUserStatement.executeUpdate();

      return true;
    }catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public void remove(Long id) {
    try {
      Statement statement = connection.createStatement();
      String sql = "DELETE from user where id =" + id;
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean update(User user, Long id) {
    try{
      PreparedStatement insertClientStatement = connection
              .prepareStatement("Update `" + USER +"` set `username`=\'" + user.getUsername() + "\', " + "`password`=\'" + user.getPassword()
                      + "\' where `id`=\'" + id + "\'");
      insertClientStatement.executeUpdate();

      return true;
    }catch (SQLException e){
      e.printStackTrace();
      return false;
    }
  }

  private User getUserFromResultSet(ResultSet rs) throws SQLException {
    return new UserBuilder()
            .setUsername(rs.getString("username"))
            .setPassword(rs.getString("password"))
            .build();
  }
}
