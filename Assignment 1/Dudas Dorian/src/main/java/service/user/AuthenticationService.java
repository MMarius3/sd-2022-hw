package service.user;

import model.Role;
import model.User;

import java.util.List;

public interface AuthenticationService {

  boolean register(String username, String password);

  User login(String username, String password);

  boolean logout(User user);

}
