package service.activity;

import model.ActivityUser;
import model.User;
import repository.activity.ActivityUserRepository;

import java.util.Date;
import java.util.List;

public class ActivityUserServiceMySQL implements ActivityUserService{
    private final ActivityUserRepository activityUserRepository;

    public ActivityUserServiceMySQL(ActivityUserRepository activityUserRepository) {
        this.activityUserRepository = activityUserRepository;
    }


    @Override
    public boolean add(Long user_id, String activity, Date date,String username) {
        ActivityUser activityUser = new ActivityUser();
        activityUser.setUser_id(user_id);
        activityUser.setActivity(activity);
        activityUser.setPeriod(date);
        activityUser.setUsername(username);
        System.out.println(username+"->2");

        return activityUserRepository.save(activityUser);
    }

    @Override
    public List<ActivityUser> view(Date period, String username) {
        return activityUserRepository.findByPeriodAndUsername(period,username);
    }
}
