package repository.user;

import model.User;
import repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<Long, User> {
  Optional<User> findByUsernameAndPassword(String username, String password);
}
