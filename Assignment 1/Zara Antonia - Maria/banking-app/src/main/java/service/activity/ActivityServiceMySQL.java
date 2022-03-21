package service.activity;

import model.Activity;
import model.User;
import repository.activity.ActivityRepository;

import java.util.ArrayList;

public class ActivityServiceMySQL implements ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ArrayList<Activity> findAllByEmployee(User user) {
        return activityRepository.findAllByEmployee(user);
    }

    @Override
    public boolean add(Activity activity) {
        return activityRepository.add(activity);
    }
}
