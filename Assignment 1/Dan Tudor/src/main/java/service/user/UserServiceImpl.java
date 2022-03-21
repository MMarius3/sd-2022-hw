package service.user;

import model.User;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User with username %s not found".formatted(username)));
    }

    @Override
    public boolean create(User user) {
        return repository.create(user);
    }

    @Override
    public void remove(Long id) {
        repository.remove(id);
    }

    @Override
    public boolean update(User user, Long id) {
        return repository.update(user,id);
    }
}
