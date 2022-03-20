package service.activity;

import model.Activity;
import model.User;

import java.util.List;

public interface ActivityService {

    Activity getActivityById(int id);

    boolean addActivityToUser(User user, int activityId);

    Activity save(String name);

    List<Activity> getActivitiesForUser(User user);

}
