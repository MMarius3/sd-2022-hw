package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    boolean save(Account account);

    boolean removeById(long id);

    Account update(Account account);
}
