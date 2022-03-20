package service.user;

import model.Client;
import model.User;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id %d not found".formatted(id)));
    }

    public User findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("User with name %s not found".formatted(name)));
    }

    @Override
    public boolean save(User user) {
        return repository.save(user);
    }
}
