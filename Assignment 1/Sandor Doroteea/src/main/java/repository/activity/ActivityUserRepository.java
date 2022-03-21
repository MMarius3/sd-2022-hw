package repository.activity;

import model.ActivityUser;

import java.util.Date;
import java.util.List;

public interface ActivityUserRepository {
    boolean save(ActivityUser au);
    List<ActivityUser> findByPeriodAndUsername(Date period, String username);

}
