package repository.user;

import controller.Response;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    boolean removeAll();

    Response<Boolean> existsByUsername(String email);

    ArrayList<User> findAllEmployees();

    boolean update(User user);

    public boolean delete(User user);

    Optional<User> findById(Integer id);
}