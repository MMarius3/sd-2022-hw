package service.user;

import javafx.collections.ObservableList;
import model.Action;
import model.Client;
import model.User;

import java.sql.Date;
import java.util.List;

public interface UserService {
    ObservableList<User> findAllEmployees();


    boolean edit(User user);

    boolean addAction(Long userId, String description);

    boolean remove(User selectedItem);

    ObservableList<Action> findAllActions(Long userId, Date startDate, Date endDate);

    void generateReport(User user, List<Action> actions, Date startDate, Date endDate);
}
