package repository.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import service.user.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.enums.TableTypeSQL.USER;

public class UserRepositoryMySQL implements UserRepository {

  private final Connection connection;
  private final RightsRolesRepository rightsRolesRepository;

  public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
    this.connection = connection;
    this.rightsRolesRepository = rightsRolesRepository;
  }

  @Override
  public List<User> findAll() {
    String sql = "SELECT * FROM %s".formatted(USER.getLabel());
    List<User> users = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      while(resultSet.next()) {
        users.add(getUserFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return users;
  }

  @Override
  public Optional<User> findById(Long id) {
    String sql = "SELECT * FROM %s WHERE id = ?".formatted(USER.getLabel());

    try {
      PreparedStatement findStatement = connection.prepareStatement(sql);
      findStatement.setLong(1, id);
      ResultSet resultSet = findStatement.executeQuery();
      if(resultSet.next()) {
        return Optional.of(getUserFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public boolean save(User user) {
    String sql = "INSERT INTO %s VALUES(?, ?, ?)".formatted(USER.getLabel());

    try {
      PreparedStatement insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      if(user.getId() == null) {
        insertStatement.setNull(1, Types.NULL);
      }
      else {
        insertStatement.setLong(1, user.getId());
      }
      insertStatement.setString(2, user.getUsername());
      insertStatement.setString(3, user.getPassword());
      insertStatement.executeUpdate();

      ResultSet rs = insertStatement.getGeneratedKeys();
      rs.next();
      long userId = rs.getLong(1);
      user.setId(userId);

      rightsRolesRepository.addRolesToUsers(user, user.getRoles());

      return true;
    } catch (SQLException throwables) {
      return false;
    }
  }

  @Override
  public boolean update(Long id, User user) {
    String sql = "UPDATE %s SET username=?, password=? WHERE id=?".formatted(USER.getLabel());
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, user.getUsername());
      preparedStatement.setString(2, PasswordEncoder.encodePassword(user.getPassword()));
      preparedStatement.setLong(3, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean deleteById(Long id) {
    String sql = "DELETE FROM %s WHERE ID = ?".formatted(USER.getLabel());
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM %s WHERE id >= 0".formatted(USER.getLabel());

    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Override
  public Optional<User> findByUsernameAndPassword(String username, String password) {
    try {
      Statement statement = connection.createStatement();

      String fetchUserSql =
              "Select * FROM %s where `username`='%s' and `password`='%s'".formatted(USER.getLabel(), username, password);
      ResultSet userResultSet = statement.executeQuery(fetchUserSql);
      userResultSet.next();
      return Optional.of(getUserFromResultSet(userResultSet));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
    Long userId = resultSet.getLong("id");
    List<Role> userRoles = rightsRolesRepository.findRolesForUsers(userId);

    return new UserBuilder()
            .setId(userId)
            .setUsername(resultSet.getString("username"))
            .setPassword(resultSet.getString("password"))
            .setRoles(userRoles)
            .build();
  }
}
