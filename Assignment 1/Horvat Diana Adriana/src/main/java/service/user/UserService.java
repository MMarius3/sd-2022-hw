package service.user;

import controller.Response;
import model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username);

    boolean save(User user);

    boolean removeUser(User user);

    boolean updateUser(User user);

    User findById(int id);

    List<Integer> getAllActivities(User user);
}
