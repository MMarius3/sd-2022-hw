package repository.user;

import controller.Response;
import model.Client;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findById(Long id);

    User findByExistentId(Long id);

    boolean save(User user);

    boolean update(Long id, String name);

    boolean delete(Long id);

    void removeAll();

    Response<Boolean> existsByUsername(String email);
}