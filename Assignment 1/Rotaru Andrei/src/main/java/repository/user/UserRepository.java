package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    List<User> findAllEmployees();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    boolean update(Long id, String username);

    boolean delete(User user);

    void removeAll();

    Notification<User> findByUsername(String username);
}
