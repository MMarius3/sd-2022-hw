package service.activity;

import model.Activity;
import model.User;

import java.util.ArrayList;

public interface ActivityService {

    ArrayList<Activity> findAllByEmployee(User user);

    boolean add(Activity activity);
}
