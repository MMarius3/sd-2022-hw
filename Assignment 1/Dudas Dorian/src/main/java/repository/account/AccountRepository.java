package repository.account;

import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    Optional<Account> findById(Long id);

    boolean save(Account account);

    boolean updateById(Long id, Account account);

    boolean removeById(Long id);

    void removeAll();
}
