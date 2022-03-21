package repository.user;

import controller.Response;
import model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll(int role_id);

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    Response<Boolean> existsByUsername(String email);

    boolean updateUser(User user);

    boolean deleteUser(Long id);
}
