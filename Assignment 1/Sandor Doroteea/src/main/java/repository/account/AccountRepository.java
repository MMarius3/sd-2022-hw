package repository.account;

import model.Account;
import model.Client;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    List<Account> findAllClientAccounts(Long id);

    Account findByClientID(Long id);

    boolean save(Account account);

    void removeAll();

    Account findById(Long id);

    boolean removeById(Long id);

    boolean update(Account account);
}
