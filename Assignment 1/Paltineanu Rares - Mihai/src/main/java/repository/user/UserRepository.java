package repository.user;

import controller.Response;
import model.User;
import repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    Response<Boolean> existsByUsername(String username);

    void removeAll();
}
