package repository.user;

import controller.Response;
import model.Action;
import model.User;

import java.sql.Date;
import java.util.List;

public interface UserRepository {

  List<User> findAllEmployees();

  User findByUsernameAndPassword(String username, String password);

  boolean save(User user);

  void removeAll();

  Response<Boolean> existsByUsername(String email);

  boolean edit(User user);

    boolean addAction(Action action);

   boolean remove(User user);

    List<Action> findAllActions(Long userId, Date startDate, Date endDate);
}
