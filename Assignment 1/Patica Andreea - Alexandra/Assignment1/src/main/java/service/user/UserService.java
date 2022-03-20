package service.user;

import model.Client;
import model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User findByName(String name);

    boolean save(User user);
}
