package service.activity;

import model.Activity;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface ActivityService {
    boolean save(Activity activity);

    List<Activity> findByDateAndId(Long id, String startDate, String endDate);

    void removeAll();
}
