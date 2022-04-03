package service.Service;

import model.User;
import model.validation.Notification;

public interface UserService {
    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password);

    boolean logout(User user);
}