package service.user;

import model.Account;
import model.User;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceMySQL implements UserService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public boolean update(Long id, User newUser) {
        return userRepository.update(id, newUser);
    }

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeAll() {
        userRepository.removeAll();
    }
}
