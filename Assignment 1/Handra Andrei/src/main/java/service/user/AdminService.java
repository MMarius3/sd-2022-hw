package service.user;

import model.Action;
import model.User;

import java.util.Date;
import java.util.List;

public interface AdminService {

    boolean createEmployee(String username, String password);

    List<User> findAll();

    boolean updateEmployee(Long id,String username,String password);

    boolean deleteEmployee(Long id);

    List<Action> generateReport(Long id, Date date1, Date date2);
}
