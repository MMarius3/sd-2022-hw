package service.employee;

import model.User;
import repository.user.UserRepository;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{


    private final UserRepository userRepository;

    public EmployeeServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id %d not found".formatted(id)));

    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateEmail(User user) {
        userRepository.updateEmail(user);
    }
}
