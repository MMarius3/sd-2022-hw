package respository.activity;

import model.Activity;
import model.Role;
import model.User;
import model.builder.UserBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryMySQL implements ActivityRepository{

    private final Connection connection;
    public static final String ACTIVITY = "activities";
    public static final String ACTIVITY_USER = "activity_user";

    public ActivityRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public Activity getActivityById(int id){
        try {
            Statement statement = connection.createStatement();

            String fetchActivitySql =
                    "Select * from `" + ACTIVITY + "` where `id`=\'" + id + "\'";
            ResultSet activityResultSet = statement.executeQuery(fetchActivitySql);
            activityResultSet.next();

            Activity activity = new Activity(activityResultSet.getInt("id"), activityResultSet.getString("activity"),
                    activityResultSet.getTimestamp("created_at"));

            return activity;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean addActivityToUser(User user, int activityId){
        try {
            PreparedStatement insertActivityUserStatement = connection
                    .prepareStatement("INSERT INTO `activity_user` values (null, ?, ?, null)");
            insertActivityUserStatement.setInt(1, user.getId());
            insertActivityUserStatement.setInt(2, activityId);
            insertActivityUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Activity save(String name){
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO activities values (null, ?, null)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, name);
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            int activityId = rs.getInt(1);

            Activity activity = new Activity(activityId, name);

            return activity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> getActivitiesPerformedByAUser(User user){
        try {
            List<Activity> activities = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACTIVITY_USER + " where `user_id`=\'" + user.getId() + "\'";
            ResultSet userRoleResultSet = statement.executeQuery(fetchRoleSql);
            while (userRoleResultSet.next()) {
                int activityId = userRoleResultSet.getInt("activity_id");
                activities.add(getActivityById(activityId));
            }
            return activities;
        } catch (SQLException e) {

        }
        return null;
    }
}
