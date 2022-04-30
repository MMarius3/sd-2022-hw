package repository.account;

import model.Account;
import repository.Cache;

import java.util.List;
import java.util.Optional;

public class AccountRepositoryCacheDecorator extends AccountRepositoryDecorator {

    private Cache<Account> cache;

    public AccountRepositoryCacheDecorator(AccountRepository accountRepository, Cache<Account> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Account> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Account> clients = accountDecoratedRepository.findAll();
        cache.save(clients);
        return clients;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDecoratedRepository.findById(id);
    }

    @Override
    public Long lastId() {
        cache.invalidateCache();
        return accountDecoratedRepository.lastId();
    }

    @Override
    public boolean save(Account account) {
        cache.invalidateCache();
        return accountDecoratedRepository.save(account);
    }

    @Override
    public boolean update(Account account) {
        cache.invalidateCache();
        return accountDecoratedRepository.update(account);
    }

    @Override
    public boolean delete(Long id) {
        cache.invalidateCache();
        return accountDecoratedRepository.delete(id);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        accountDecoratedRepository.removeAll();
    }
}
