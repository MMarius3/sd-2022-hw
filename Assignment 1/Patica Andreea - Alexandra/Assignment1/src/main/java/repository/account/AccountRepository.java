package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findByClientId(Long id);

    boolean save(Account account);

    boolean update(Account account);

    boolean updateBalance(Account account, Long balance);

    boolean delete(Account account);
}
