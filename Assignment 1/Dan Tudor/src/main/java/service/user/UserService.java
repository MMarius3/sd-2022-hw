package service.user;

import model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    boolean create(User user);

    void remove(Long id);

    boolean update(User user);
}
