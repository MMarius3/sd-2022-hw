package repository.user;

import controller.Response;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean update(User user);

    boolean delete(Long id);

    Response<Boolean> existsByUsername(String email);
}
