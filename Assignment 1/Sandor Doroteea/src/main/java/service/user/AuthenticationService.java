package service.user;

import model.Client;
import model.User;

import java.util.List;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    User viewByUsername(String username);
    boolean removeByUsername(String username);

    boolean update(Long id,String username, String password);
    boolean logout(User user);
    List<User> view();

}