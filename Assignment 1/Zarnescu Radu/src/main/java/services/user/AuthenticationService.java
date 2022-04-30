package services.user;

import model.User;

import java.util.List;

public interface AuthenticationService {
    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

    List<User> getEmployees();

    boolean updateUser(User user);

    boolean deleteUser(Long id);
}
