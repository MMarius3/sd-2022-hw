package repository;

import model.Account;

import java.util.List;
import java.util.Optional;

public class AccountRepository implements GenericRepository<Account>{
    @Override
    public int add(Account object) {
        return 0;
    }

    @Override
    public boolean delete(Account object) {
        return false;
    }

    @Override
    public boolean update(Account object) {
        return false;
    }

    @Override
    public Optional<Account> getByID(int id) {
        return null;
    }

    @Override
    public List<Optional<Account>> getAll() {
        return null;
    }
}
