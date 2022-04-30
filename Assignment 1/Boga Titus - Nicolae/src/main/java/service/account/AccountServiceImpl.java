package service.account;

import model.Account;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService{

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository)
    {
        this.repository= repository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account with id %d not found".formatted(id)));

    }

    @Override
    public boolean save(Account account) {
        return  repository.save(account);
    }

    @Override
    public boolean update(Account account) {
        return repository.update(account);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Account> findByClientId(Long id) {
        return repository.findByClientId(id);
    }
}
