package service.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface CRUDUser {
    List<User> findAll();
    Notification<Boolean> save(User user);
    Notification<Boolean> update(Long id, String username, String unEncodedPass);
    Notification<Boolean> updateUsername(Long id, String username, String password);
    Notification<Boolean> updatePassword(Long id, String username, String pass);
    User findByID(Long id);
    boolean delete(Long id);
}
