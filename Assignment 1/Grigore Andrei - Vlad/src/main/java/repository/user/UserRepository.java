package repository.user;

import controller.Response;
import model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findByUsernameAndPassword(String username);

    boolean save(User user);
    void remove(User user);
    void removeAll();

    Response<Boolean> existsByUsername(String email);
}
