package repository;

import model.User;

import java.util.List;
import java.util.Optional;

public class UserRepository implements GenericRepository<User>{
    @Override
    public int add(User object) {
        return 0;
    }

    @Override
    public boolean delete(User object) {
        return false;
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public Optional<User> getByID(int id) {
        return null;
    }

    @Override
    public List<Optional<User>> getAll() {
        return null;
    }

    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return null;
    }
}
