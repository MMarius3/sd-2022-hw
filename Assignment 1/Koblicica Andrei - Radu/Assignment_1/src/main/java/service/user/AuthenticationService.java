package service.user;

import model.User;

public interface AuthenticationService {

  User register(String username, String password);

  User login(String username, String password);

  boolean logout(User user);

  String getUserType(Long id);

  String encodePassword(String password);

}
