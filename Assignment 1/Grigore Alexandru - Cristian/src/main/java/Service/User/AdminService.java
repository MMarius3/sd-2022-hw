package Service.User;

import Model.Action;
import Model.User;

import java.util.Date;
import java.util.List;

public interface AdminService {

    List<User> findAll();

    void addEmployee(User user);

    void editEmployee(User user);

    void deleteEmployee(User user);

    void generateReport(User user, Date start, Date end, List<Action> actions);

}
