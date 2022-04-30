package repository_layer.repository.user;

import bussiness_layer.models.User;

public interface UserRepository {

  Long insert (User user);

  boolean remove (String username);

  boolean update (String username, User newUser);

  User findByUsernameAndPassword(String username, String password);

  User findById(Long id);

  User findByUsername(String username);

  User findByPnc(String pnc);

  void removeAll();
}
