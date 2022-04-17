package service.user;

import model.Role;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface AuthenticationService {

  Notification<Boolean> register(String username, String password);

  Notification<User> login(String username, String password);

  List<Role> getRole(Long userId);

  boolean logout(User user);


}
