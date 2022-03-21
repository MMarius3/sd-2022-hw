package service.activity;

import model.ActivityUser;

import java.util.Date;
import java.util.List;

public interface ActivityUserService {
    public boolean add(Long user_id, String activity, Date date,String username);
    List<ActivityUser> view(Date period,String username);
}
