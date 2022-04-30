package service.user;

import model.User;
import respository.client.ClientRepository;
import respository.user.UserRepository;

import java.util.List;

public class UserServiceMySQL implements UserService {

    private final UserRepository userRepository;
    public UserServiceMySQL(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean save(User user){
        return userRepository.save(user);
    }

    @Override
    public boolean removeUser(User user){
        return userRepository.removeUser(user);
    }

    @Override
    public boolean updateUser(User user){
        return userRepository.updateUser(user);
    }

    @Override
    public User findById(int id){
        return userRepository.findById(id);
    }

    @Override
    public List<Integer> getAllActivities(User user){
        return userRepository.getAllActivities(user);
    }
}
