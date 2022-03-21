package repository.user;

import controller.Response;
import model.User;

import java.util.List;

public interface UserRepository {

    String getPassword(String username);

    List<User> findAll();

    User findById(Long id);

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    Response<Boolean> existsByUsername(String email);

    boolean update(User user);


}
