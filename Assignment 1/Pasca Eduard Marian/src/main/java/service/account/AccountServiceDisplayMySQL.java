package service.account;

import model.Account;
import repository.account.AccountRepositoryMySQL;

import java.util.List;

public class AccountServiceDisplayMySQL implements AccountServiceDisplay{

    private final AccountRepositoryMySQL accountRepositoryMySQL;

    public AccountServiceDisplayMySQL(AccountRepositoryMySQL accountRepositoryMySQL) {
        this.accountRepositoryMySQL = accountRepositoryMySQL;
    }

    @Override
    public List<Account> findAll() {
        return accountRepositoryMySQL.findAll();
    }
}
