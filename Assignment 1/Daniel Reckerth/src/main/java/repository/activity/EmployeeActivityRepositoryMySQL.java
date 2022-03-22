package repository.activity;

import model.EmployeeActivity;
import model.Right;
import model.User;
import model.builder.EmployeeActivityBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.enums.TableTypeSQL.ACTIVITY;

public class EmployeeActivityRepositoryMySQL implements EmployeeActivityRepository {

  private final Connection connection;
  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;

  public EmployeeActivityRepositoryMySQL(Connection connection, UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
    this.connection = connection;
    this.userRepository = userRepository;
    this.rightsRolesRepository = rightsRolesRepository;
  }

  @Override
  public List<EmployeeActivity> findAll() {
    String sql = "SELECT * FROM %s".formatted(ACTIVITY.getLabel());
    List<EmployeeActivity> activities = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      while(resultSet.next()) {
        activities.add(getEmployeeActivityFromResultSet(resultSet));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return activities;
  }

  @Override
  public Optional<EmployeeActivity> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public boolean save(EmployeeActivity employeeActivity) {
    String sql = "INSERT INTO %s VALUES(?, ?, ?, ?)".formatted(ACTIVITY.getLabel());
    try {
      Long employeeId = employeeActivity.getEmployee().getId();
      final Optional<User> employeeOptional = userRepository.findById(employeeId);
      if(employeeOptional.isPresent()) {
        PreparedStatement insertStatement = connection.prepareStatement(sql);
        if (employeeActivity.getId() == null) {
          insertStatement.setNull(1, Types.NULL);
        } else {
          insertStatement.setLong(1, employeeActivity.getId());
        }
        insertStatement.setTimestamp(2, Timestamp.valueOf(employeeActivity.getPerformedAt()));
        insertStatement.setLong(3, employeeId);
        insertStatement.setLong(4, employeeActivity.getPerformedActivity().getId());
        insertStatement.executeUpdate();
        return true;
      }
    } catch (SQLException | NullPointerException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean update(Long id, EmployeeActivity updatingActivity) {
    return false;
  }

  @Override
  public boolean deleteById(Long id) {
    String sql = "DELETE FROM %s WHERE ID = ?".formatted(ACTIVITY.getLabel());
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
    String sql = "DELETE FROM %s WHERE id >= 0".formatted(ACTIVITY.getLabel());

    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  private EmployeeActivity getEmployeeActivityFromResultSet(ResultSet resultSet) throws SQLException {
    EmployeeActivity activity = new EmployeeActivityBuilder()
            .setId(resultSet.getLong("id"))
            .setPerformedAt(resultSet.getTimestamp("performed_at").toLocalDateTime())
            .build();

    final Optional<User> userOptional = userRepository.findById(resultSet.getLong("employee_id"));
    final Optional<Right> rightOptional = rightsRolesRepository.findRightById(resultSet.getLong("activity_id"));
    userOptional.ifPresent(activity::setEmployee);
    rightOptional.ifPresent(activity::setPerformedActivity);

    return activity;
  }
}
