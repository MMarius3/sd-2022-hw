package service.account;

import model.Account;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.repository = accountRepository;
    }


    @Override
    public List<Account> findByClientId(Long id) {
        return repository.findByClientId(id);
    }

    @Override
    public boolean save(Account account) {
        return repository.save(account);
    }

    @Override
    public boolean update(Account account) {
        return repository.update(account);
    }

    @Override
    public boolean updateBalance(Account account, Long balance) {
        return repository.updateBalance(account, balance);
    }

    @Override
    public boolean delete(Account account) {
        return repository.delete(account);
    }
}
