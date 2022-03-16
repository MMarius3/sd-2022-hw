package repository.account;

import model.Account;

import java.sql.Connection;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account findById(Long id) {
        return null;
    }

    @Override
    public boolean save(Account entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean update(Long id, Account newEntity) {
        return false;
    }
}
