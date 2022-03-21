package repository.user;

import controller.Response;
import model.Account;
import model.Action;
import model.User;
import model.builder.ActionBuilder;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Tables.*;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

  private final Connection connection;
  private final RightsRolesRepository rightsRolesRepository;


  public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
    this.connection = connection;
    this.rightsRolesRepository = rightsRolesRepository;
  }

  @Override
  public List<User> findAllEmployees() {
    List<User> users=new ArrayList<>();
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
              "Select * from `" + USER + "` JOIN `" + USER_ROLE + "` ON user.id = user_role.user_id WHERE role_id='2'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      while(userResultSet.next()){
        User user = new UserBuilder()
                .setId(userResultSet.getLong("id"))
                .setUsername(userResultSet.getString("username"))
                .setPassword(userResultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                .build();
        users.add(user);
      }
      return users;
    } catch (SQLException e) {
      System.out.println(e.toString());
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

      User user = new UserBuilder()
              .setId(userResultSet.getLong("id"))
          .setUsername(userResultSet.getString("username"))
          .setPassword(userResultSet.getString("password"))
          .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
          .build();

      return user;
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

  public boolean remove(User user){
    try {
      PreparedStatement statement = connection
              .prepareStatement("DELETE FROM user WHERE id=?");
      statement.setLong(1, user.getId());
      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<Action> findAllActions(Long userId, Date startDate, Date endDate) {
    List<Action> actions=new ArrayList<>();
    try {
      Statement statement = connection.createStatement();

      String sql =
              "Select * from `" + ACTION + "` WHERE date BETWEEN \'"+startDate+"\' AND \'"+endDate+"\'";
      ResultSet userResultSet = statement.executeQuery(sql);
      while(userResultSet.next()){
        Action action = new ActionBuilder()
                .setId(userResultSet.getLong("id"))
                .setDescription(userResultSet.getString("description"))
                .setDate(userResultSet.getDate("date"))
                .build();
        actions.add(action);
      }
      return actions;
    } catch (SQLException e) {
      System.out.println(e.toString());
    }
    return null;
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

  @Override
  public boolean addAction(Action action) {
    try {
      PreparedStatement statement = connection
              .prepareStatement("INSERT INTO action values (null, ?, ?, ?)");
      statement.setLong(1, action.getUserId());
      statement.setString(2, action.getDescription());
      statement.setDate(3,action.getDate());
      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }


  @Override
  public boolean edit(User user) {
    try {
      PreparedStatement editStatement = connection
              .prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");
      editStatement.setString(1, user.getUsername());
      editStatement.setString(2, user.getPassword());
      editStatement.setLong(3, user.getId());
      editStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }




}
