package repository.activity;

import model.Activity;
import model.User;

import java.util.ArrayList;

public interface ActivityRepository {

    ArrayList<Activity> findAllByEmployee(User user);

    boolean add(Activity activity);
}
