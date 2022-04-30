package repository.user;

import controller.Response;
import model.Role;
import model.User;

import java.util.List;

public interface UserRepository {

  List<User> findAllWithRole(Role role);

  User findById(Long id);

  User findByUsernameAndPassword(String username, String password);

  boolean save(User user);

  boolean updateById(Long id, User user);

  boolean removeById(Long id);

  void removeAll();

  Response<Boolean> existsByUsername(String email);
}
