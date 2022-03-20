package repository.user;

//import controller.Response;
import controller.Response;
import model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void updateUser(Long userId, String newUsername);

    void removeAll();

    void removeUser(Long id);

    Response<Boolean> existsByUsername(String email);

    User findById(Long id);
}