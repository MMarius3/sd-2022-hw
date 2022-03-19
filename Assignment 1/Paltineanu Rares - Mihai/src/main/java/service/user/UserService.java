package service.user;

import model.Account;
import model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    boolean delete(Long id);

    boolean update(Long id, User newUser);

    boolean save(User user);
}
