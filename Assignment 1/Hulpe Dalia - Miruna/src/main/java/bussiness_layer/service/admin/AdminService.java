package bussiness_layer.service.admin;

import bussiness_layer.models.Action;
import bussiness_layer.models.User;

import java.sql.Date;
import java.util.List;

public interface AdminService {

  List<User> getAllEmployees ();

  boolean addEmployee (User user);

  boolean updateEmployee (String username, User newUser);

  boolean deleteEmployee (String username);

  User findByUsername (String username);

  List<Action> getReport (Date startDate, Date endDate, String username);

}
