package services.admin;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface AdminService {

    Notification<Boolean> createEmployee(String username, String password);

    Notification<Boolean> deleteEmployee(String username);

    List<User> viewAllEmployees();

}
