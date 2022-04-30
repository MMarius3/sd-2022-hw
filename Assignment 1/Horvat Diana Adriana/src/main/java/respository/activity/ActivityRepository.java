package respository.activity;

import model.Activity;
import model.User;

import java.sql.Timestamp;
import java.util.List;

public interface ActivityRepository {

    Activity save(String name);

    Activity getActivityById(int id);

    boolean addActivityToUser(User user, int activityId);

    List<Activity> getActivitiesPerformedByAUser(User user);
}
