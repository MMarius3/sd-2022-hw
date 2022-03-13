package repository.user;

import controller.Response;
import model.User;
import repository.Repository;

public interface UserRepository extends Repository<Long, User> {

    User findByUsernameAndPassword(String username, String password);

    Response<Boolean> existsByUsername(String username);
}
