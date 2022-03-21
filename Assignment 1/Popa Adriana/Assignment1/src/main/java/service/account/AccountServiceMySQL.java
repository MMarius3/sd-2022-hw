package service.account;

import model.Account;
import repository.account.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountServiceMySQL implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> viewAccount(Integer id) {
        return accountRepository.viewAccount(id);
    }

    @Override
    public boolean createAccount(Account account) {
        return accountRepository.createAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteAccount(id);
    }

    @Override
    public Optional<Account> findByClientID(Integer id){
        return accountRepository.findByClientID(id);
    }

    @Override
    public List<Account> viewAccounts(){
        return accountRepository.viewAccounts();
    }
}
