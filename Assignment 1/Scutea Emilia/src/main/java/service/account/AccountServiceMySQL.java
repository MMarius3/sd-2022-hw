package service.account;

import model.Account;

import repository.account.AccountRepository;
import java.util.List;


public class AccountServiceMySQL implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public boolean save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean update(Account account) {
        return accountRepository.update(account);
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

    @Override
    public Double selectAmount(Long id) {
        return accountRepository.selectAmount(id);
    }
}
