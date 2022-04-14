package repository.user;

import controller.Response;
import model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);
    List<User> findEmployee();
    boolean save(User user);
    void updateEmployeeUsername(Long id, String username);
    void removeAll();
    void removeById(Long id);
    User findById(Long id);
    Response<Boolean> existsByUsername(String email);
}
