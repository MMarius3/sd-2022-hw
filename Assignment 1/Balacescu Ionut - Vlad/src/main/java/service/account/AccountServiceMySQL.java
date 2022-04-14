package service.account;

import model.Account;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceMySQL implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public boolean addAccount(Account account,long clientId) {
        return accountRepository.addAccount(account,clientId);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }

    @Override
    public List<Account> getAccountsForClient(long clientId) {
        return accountRepository.getAccountsForClient(clientId);
    }

    @Override
    public Account findAccountById(long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteAccount(id);
    }
}
