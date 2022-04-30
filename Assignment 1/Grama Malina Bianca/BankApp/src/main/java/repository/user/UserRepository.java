package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean remove(Long id);

    boolean existsByUsername(String username);

    boolean update(Long id, String username, String password);

    User findById(Long id);
}
