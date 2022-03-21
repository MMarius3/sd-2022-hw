package service.user;

import model.Role;
import model.User;
import repository.user.UserRepository;

import java.util.List;

public class UserInfoServiceImpl implements UserInfoService{
    private final UserRepository userRepository;

    public UserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllWithRole(Role role) {
        return userRepository.findAllWithRole(role);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean updateById(Long id, User user) {
        return userRepository.updateById(id, user);
    }

    @Override
    public boolean removeById(Long id) {
        return userRepository.removeById(id);
    }
}
