package service.user;

import model.User;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceMySQL implements UserService {

  private final UserRepository userRepository;

  public UserServiceMySQL(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findById(Long aLong) {
    return userRepository.findById(aLong)
            .orElseThrow(() -> new IllegalArgumentException("No such id"));
  }

  // register from AuthenticationService
  @Override
  public boolean save(User element) {
    return false;
  }

  @Override
  public boolean update(Long aLong, User element) {
    return userRepository.update(aLong, element);
  }

  @Override
  public boolean deleteById(Long aLong) {
    return userRepository.deleteById(aLong);
  }

  @Override
  public void deleteAll() {
    userRepository.deleteAll();
  }
}
