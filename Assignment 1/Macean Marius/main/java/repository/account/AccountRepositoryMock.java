package repository.account;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryMock implements AccountRepository {

    private List<Account> accounts;

    public AccountRepositoryMock() {
        accounts = new ArrayList<>();
    }

    public List<Account> findAll() {
        return accounts;
    }

    public Optional<Account> findById(Long id) {
        return accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public boolean save(Account account) {
        return accounts.add(account);
    }

    public Long lastId() {
        Long maxId = 0L;
        for (Account account : accounts)
        {
            if (maxId < account.getId()) {
                maxId = account.getId();
            }
        }
        return maxId;
    }

    public boolean update(Account account) {
        return accounts.add(account);
    }

    public boolean delete(Long id) {
        return accounts.remove(accounts.get(id.intValue()));
    }

    @Override
    public void removeAll() {
        accounts.clear();
    }
}