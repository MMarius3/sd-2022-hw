package repository_layer.repository.user;

import bussiness_layer.builder.UserBuilder;
import bussiness_layer.models.User;

import java.sql.*;

import static repository_layer.database_builder.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

  private final Connection connection;


  public UserRepositoryMySQL(Connection connection)

  {
    this.connection = connection;
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
              .setId_series(userResultSet.getString("id_series"))
              .setId_number(userResultSet.getLong("id_number"))
              .setPnc(userResultSet.getString("pnc"))
              .setAddress(userResultSet.getString("address"))
              .build();

      return user;
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public User findById(Long id) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
              "Select * from `" + USER + "` where `id`= " + id;
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      userResultSet.next();

      User user = new UserBuilder()
              .setId(userResultSet.getLong("id"))
              .setUsername(userResultSet.getString("username"))
              .setPassword(userResultSet.getString("password"))
              .setId_series(userResultSet.getString("id_series"))
              .setId_number(userResultSet.getLong("id_number"))
              .setPnc(userResultSet.getString("pnc"))
              .setAddress(userResultSet.getString("address"))
              .build();

      return user;
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public User findByUsername(String username) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
              "Select * from `" + USER + "` where `username`=\'" + username + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      userResultSet.next();

      User user = new UserBuilder()
              .setId(userResultSet.getLong("id"))
              .setUsername(userResultSet.getString("username"))
              .setPassword(userResultSet.getString("password"))
              .setId_series(userResultSet.getString("id_series"))
              .setId_number(userResultSet.getLong("id_number"))
              .setPnc(userResultSet.getString("pnc"))
              .setAddress(userResultSet.getString("address"))
              .build();

      return user;
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public User findByPnc(String pnc) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
              "Select * from `" + USER + "` where `pnc`=\'" + pnc + "\'";
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      userResultSet.next();

      User user = new UserBuilder()
              .setId(userResultSet.getLong("id"))
              .setUsername(userResultSet.getString("username"))
              .setPassword(userResultSet.getString("password"))
              .setId_series(userResultSet.getString("id_series"))
              .setId_number(userResultSet.getLong("id_number"))
              .setPnc(userResultSet.getString("pnc"))
              .setAddress(userResultSet.getString("address"))
              .build();

      return user;
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public Long insert (User user) {
    try {
      PreparedStatement insertStatement = connection
              .prepareStatement("INSERT INTO " + USER + " values (null, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      insertStatement.setString(1, user.getUsername());
      insertStatement.setString(2, user.getPassword());
      insertStatement.setString(3, user.getId_series());
      insertStatement.setLong(4, user.getId_number());
      insertStatement.setString(5, user.getPnc());
      insertStatement.setString(6, user.getAddress());
      insertStatement.executeUpdate();

      try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getLong(1);
        }
        else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }


    } catch (SQLException e) {
    }
    return -1l;
  }

  @Override
  public boolean remove(String username) {
    try {
      PreparedStatement deleteStatement = connection
              .prepareStatement("DELETE FROM " + USER + " WHERE username =  ? ");
      deleteStatement.setString(1, username);
      deleteStatement.executeUpdate();

      return true;
    } catch (SQLException e) {
    }
    return false;
  }

  @Override
  public boolean update(String username, User newUser) {
    try {
      PreparedStatement updateStatement = connection
              .prepareStatement("UPDATE " + USER +" SET username = ?, password = ?, id_series = ?, id_number = ?, pnc = ?, address = ?" +" WHERE username =  ? ");
      updateStatement.setString(1, newUser.getUsername());
      updateStatement.setString(2, newUser.getPassword());
      updateStatement.setString(3, newUser.getId_series());
      updateStatement.setLong(4, newUser.getId_number());
      updateStatement.setString(5, newUser.getPnc());
      updateStatement.setString(6, newUser.getAddress());
      updateStatement.setString(7, username);
      updateStatement.executeUpdate();

      return true;
    } catch (SQLException e) {
    }
    return false;
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
}
