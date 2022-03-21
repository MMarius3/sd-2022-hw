package repository.user;

import controller.Response;
import model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    boolean save(User user);

    void removeAll();

    boolean removeByUsername(String username);

    boolean removeById(Long id);
    boolean update(User user);

    Response<Boolean> existsByUsername(String email);
}