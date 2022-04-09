package repository;

import entity.User;

import java.util.List;
import java.util.Optional;

public class UserRepository implements IUserRepository {
    @Override
    public void insert(User object) {

    }

    @Override
    public void delete(User object) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(User object) {

    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<User>> getAll() {
        return null;
    }
}
