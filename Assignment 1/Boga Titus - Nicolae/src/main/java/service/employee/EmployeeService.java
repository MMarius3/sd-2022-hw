package service.employee;

import model.User;

import java.util.List;

public interface EmployeeService {

    ///These methods would be better in a different package as this is only for autherntication
    User findUserById(Long id);

    List<User> getUsers();

    void save(User user);

    void updateEmail(User user);
}
