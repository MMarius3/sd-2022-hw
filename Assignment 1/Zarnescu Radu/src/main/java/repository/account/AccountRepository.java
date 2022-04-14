package repository.account;

import model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll() throws SQLException;

    Optional<Account> findById(Long id);

    boolean save(Account account);

    boolean update(Account account);

    void removeAll();
}
