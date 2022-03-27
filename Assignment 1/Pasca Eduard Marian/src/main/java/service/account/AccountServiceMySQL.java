package service.account;

import model.Account;
import repository.account.AccountRepositoryMySQL;

import java.util.List;

public class AccountServiceMySQL implements AccountService{

    private final AccountRepositoryMySQL accountRepositoryMySQL;

    public AccountServiceMySQL(AccountRepositoryMySQL accountRepositoryMySQL) {
        this.accountRepositoryMySQL = accountRepositoryMySQL;
    }

    @Override
    public List<Account> findAll() {
        return accountRepositoryMySQL.findAll();
    }

    @Override
    public boolean save(Account account) { return accountRepositoryMySQL.save(account); }

    @Override
    public boolean removeById(long id) { return accountRepositoryMySQL.removeById(id); }

    public Account update(Account account) { return accountRepositoryMySQL.update(account); }
}
