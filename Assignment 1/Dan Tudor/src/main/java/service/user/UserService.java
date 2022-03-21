package service.user;

import model.User;

import java.util.Optional;

public interface UserService {
    User findByUsername(String username);

    boolean create(User user);

    void remove(Long id);

    boolean update(User user, Long id);
}
