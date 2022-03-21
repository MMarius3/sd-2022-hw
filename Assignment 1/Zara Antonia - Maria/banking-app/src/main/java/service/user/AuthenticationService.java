package service.user;

import model.User;

import java.util.ArrayList;

public interface AuthenticationService {

    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

    boolean addAdmin();

    ArrayList<User> findAllEmployees();

    boolean update(User user, String username, String password);

    boolean delete(User user);
}