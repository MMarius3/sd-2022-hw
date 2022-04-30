package repository.user;

import controller.Response;
import model.User;

import java.util.List;

public interface UserRepository {

  List<User> findAll();

  User findByUsernameAndPassword(String username, String password);

  boolean save(User user);

  void removeAll();

  void deleteByUsername(String username);

  Response<Boolean> existsByUsername(String email);

  void updateByUsername(String email, User u2);

  User findByUsername(String username);
}
