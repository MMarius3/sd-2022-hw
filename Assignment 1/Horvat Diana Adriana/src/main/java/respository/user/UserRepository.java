package respository.user;

import controller.Response;
import model.Activity;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    Response<Boolean> existsByUsername(String email);

    User findByUsername(String username);

    boolean removeUser(User user);

    boolean updateUser(User user);

    User findById(int id);

    List<Integer> getAllActivities(User user);
}
