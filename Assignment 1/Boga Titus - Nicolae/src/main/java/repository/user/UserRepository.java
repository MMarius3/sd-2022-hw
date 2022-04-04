package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  List<User> findAll();

  Notification<User> findByUsernameAndPassword(String username, String password);

  boolean save(User user);

  Optional<User> findUserById(Long id);

  void removeAll();

  void updateEmail(User user);

}
