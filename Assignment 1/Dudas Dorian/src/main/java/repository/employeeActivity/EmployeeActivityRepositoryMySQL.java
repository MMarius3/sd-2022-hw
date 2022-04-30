package repository.employeeActivity;

import model.Client;
import model.EmployeeActivity;
import model.builder.ClientBuilder;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeActivityRepositoryMySQL implements EmployeeActivityRepository{
    private final Connection connection;

    public EmployeeActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<EmployeeActivity> findAll() {
        List<EmployeeActivity> employeeActivities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from employee_activity";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                employeeActivities.add(getEmployeeActivityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeActivities;
    }

    @Override
    public Optional<EmployeeActivity> findById(Long id) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("Select * from employee_activity where id = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                return Optional.of(getEmployeeActivityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean save(EmployeeActivity employeeActivity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO employee_activity values (null, ?, ?, ?)");
            insertStatement.setLong(1, employeeActivity.getEmployeeId());
            insertStatement.setLong(2, employeeActivity.getActionId());
            insertStatement.setDate(3, new Date(employeeActivity.getDateOfAction().getTime()));
            insertStatement.executeUpdate();
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
            String sql = "DELETE from employee_activity where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private EmployeeActivity getEmployeeActivityFromResultSet(ResultSet rs) throws SQLException {
        return new EmployeeActivity(rs.getLong("id"), rs.getLong("employee_id"),
                rs.getLong("action_id"),
                new java.util.Date(rs.getDate("action_date").getTime()));
    }

    @Override
    public List<String> generateReport(java.util.Date startDate, java.util.Date endDate, Long id) {

        List<EmployeeActivity> employeeActivities = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("Select * from employee_activity where employee_id=?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                employeeActivities.add(getEmployeeActivityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        List<String> formattedReport = employeeActivities.stream()
                .filter(x -> !x.getDateOfAction().before(startDate))
                .filter(x -> !x.getDateOfAction().after(endDate))
                .map(x -> "Employee " + userRepository.findById(x.getEmployeeId()).getUsername()  + " performed action '" + rightsRolesRepository.findRightById(x.getActionId()).getRight()  + "' at the time " + x.getDateOfAction().toString() + "\n")
                .collect(Collectors.toList());
        return formattedReport;
    }
}
