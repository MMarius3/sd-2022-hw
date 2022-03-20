package service.user;

import model.User;
import repository.client.ClientRepository;

import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final ClientRepository repository;
    public UserServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public boolean update(User user) {
        return false;
    }
}
