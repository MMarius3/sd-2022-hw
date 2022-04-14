package repository.activity;

import model.Activity;
import model.Client;
import model.Role;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import repository.user.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static database.Constants.Roles.EMPLOYEE;

public class ActivityRepositoryMySQL implements ActivityRepository{
    private final Connection connection;
    private final UserRepository userRepository;

    public ActivityRepositoryMySQL(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<Activity> findAllByEmployee(User user) {
        List<Activity> activities = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("Select * from activity where employee_id=?");
            statement.setInt(1,user.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                activities.add(getActivityFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Activity>) activities;
    }

    private Activity getActivityFromResultSet(ResultSet rs) throws SQLException {
        Optional<User> user = userRepository.findById(rs.getInt("employee_id"));

        if(user.isPresent()) {
            return new ActivityBuilder()
                    .setId(rs.getInt("id"))
                    .setDescription(rs.getString("description"))
                    .setDate(rs.getDate("activity_date"))
                    .setEmployee(user.get())
                    .build();
        }
        else{
            return new Activity();
        }
    }

    @Override
    public boolean add(Activity activity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO activity values (null, ?, ?, ?)");
            insertStatement.setInt(1, activity.getEmployee().getId());
            insertStatement.setString(2, activity.getDescription());
            insertStatement.setDate(3, activity.getDate());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
