package service.activity;

import model.Activity;
import model.User;
import respository.activity.ActivityRepository;
import respository.client.ClientRepository;

import java.util.List;

public class ActivityServiceMySQL implements ActivityService{

    private final ActivityRepository activityRepository;

    public ActivityServiceMySQL(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity getActivityById(int id){
        return activityRepository.getActivityById(id);
    }

    @Override
    public boolean addActivityToUser(User user, int activityId){
        return activityRepository.addActivityToUser(user, activityId);
    }

    @Override
    public Activity save(String name){
        return activityRepository.save(name);
    }

    @Override
    public List<Activity> getActivitiesForUser(User user){
        return activityRepository.getActivitiesPerformedByAUser(user);
    }

}
