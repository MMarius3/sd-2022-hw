package repository.user;

import model.Client;
import model.User;
import model.validation.Notification;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  List<User> findAll();

  Notification<User> findByUsernameAndPassword(String username, String password);

  boolean save(User user);

  void removeAll();

  Optional<User> findByUsername(String username);

  boolean create(User user);

  void remove(Long id);

  boolean update(User user, Long id);

}
