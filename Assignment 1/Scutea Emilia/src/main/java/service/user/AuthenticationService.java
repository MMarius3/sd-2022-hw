package service.user;

import model.User;

import java.util.List;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

    boolean createAdministrator(String username, String password);

    String encodePassword(String password);

    User findById(Long id);

    boolean update(User user);

    List<User> findAll();

}
