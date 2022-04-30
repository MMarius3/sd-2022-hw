package services.account;

import model.Account;
import repository.account.AccountRepository;

import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() throws SQLException {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Account with id %d not found", id)));
    }

    @Override
    public boolean save(Account account) {
        return false;
    }

    @Override
    public boolean update(Account account) {
        return accountRepository.update(account);
    }
}
