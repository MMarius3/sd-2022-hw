package repository.activity;

import model.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityRepository {
    boolean save(Activity activity);

    List<Activity> findByDateAndId(String startDate, String endDate, Long id);

    void removeAll();
}
