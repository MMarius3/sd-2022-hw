package repository.activity;

import model.Account;
import model.ActivityUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ActivityUserRepositoryMySQL implements ActivityUserRepository{
    private final Connection connection;

    public ActivityUserRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(ActivityUser au) {
        try {
            PreparedStatement insertActivityUserStatement = connection
                    .prepareStatement("INSERT INTO activity_user values (default , ?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insertActivityUserStatement.setLong(1, au.getUser_id());
            insertActivityUserStatement.setString(2, au.getActivity());
            insertActivityUserStatement.setDate(3, (new java.sql.Date(au.getPeriod().getTime())));
            insertActivityUserStatement.setString(4,au.getUsername());
            insertActivityUserStatement.executeUpdate();

            ResultSet rs = insertActivityUserStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            au.setId(clientId);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ActivityUser> findByPeriodAndUsername(java.util.Date period, String username) {
        List<ActivityUser> activities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from activity_user where `period`=\'" + new java.sql.Date(period.getTime()) + "\' and `username` =\'"+username+"\'";
            ResultSet activityResultSet = statement.executeQuery(fetchUserSql);

            while (activityResultSet.next()) {

                ActivityUser au = new ActivityUser();
                au.setId(activityResultSet.getLong("id"));
                au.setUser_id(activityResultSet.getLong("user_id"));
                au.setActivity(activityResultSet.getString("activity"));
                au.setPeriod(activityResultSet.getDate("period"));
                au.setUsername(activityResultSet.getString("username"));
                activities.add(au);
            }
            return activities;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}

