package service.activity;

import model.Activity;
import repository.activity.ActivityRepository;
import repository.security.RightsRolesRepository;

import java.util.Date;
import java.util.List;

public class ActivityServiceImpl implements ActivityService{
    private final ActivityRepository activityRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, RightsRolesRepository rightsRolesRepository){
        this.activityRepository = activityRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    @Override
    public boolean save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> findByDateAndId(Long id, String startDate, String endDate) {
        return activityRepository.findByDateAndId(startDate,endDate,id);
    }

    @Override
    public void removeAll() {
        activityRepository.removeAll();
    }
}
