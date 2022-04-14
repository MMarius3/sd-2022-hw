package service.employee;

import model.User;

import java.util.List;

public interface EmployeeService {
    List<User> findAllEmployees();
    boolean deleteEmployee(long id);
    boolean updateEmployee(User employee);
    User findById(long id);
    boolean addEmployee(User employee);
}
