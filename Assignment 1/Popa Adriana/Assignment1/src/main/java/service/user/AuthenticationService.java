package service.user;

import model.User;

import java.util.Optional;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

    boolean delete(String username);

    boolean update(Long id, String username,String password);

    Optional<User> findById(Long id);

}