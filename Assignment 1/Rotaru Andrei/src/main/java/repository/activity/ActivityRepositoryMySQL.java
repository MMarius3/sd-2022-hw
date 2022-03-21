package repository.activity;

import model.Activity;
import model.builder.ActivityBuilder;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class ActivityRepositoryMySQL implements ActivityRepository{
    private final Connection connection;

    public ActivityRepositoryMySQL (Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Activity activity) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO `activity` VALUES (null, ?, ?, ?, ?)");
            insertStatement.setLong(1, activity.getIdEmployee());
            insertStatement.setString(2, activity.getRole());
            insertStatement.setDate(3, activity.getDate());
            insertStatement.setString(4, activity.getDescription());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Activity> findByDateAndId(String startDate, String endDate, Long id) {
        List<Activity> activities= new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String fetchAccountSql = "SELECT * FROM activity" + " WHERE user_id = \'" + id +
                    "\' AND date(activity.date) BETWEEN \'" + startDate +
                    "\' AND \'" + endDate + "\'";
            ResultSet activityResultSet = statement.executeQuery(fetchAccountSql);

            while (activityResultSet.next()) {
                activities.add(getActivityFromResultSet(activityResultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from activity where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Activity getActivityFromResultSet(ResultSet rs) throws SQLException{
        return new ActivityBuilder()
                .setId(rs.getLong("id"))
                .setIdEmployee(rs.getLong("user_id"))
                .setRole(rs.getString("user_role"))
                .setDate(rs.getDate("date"))
                .setDescription(rs.getString("description"))
                .build();
    }
}
