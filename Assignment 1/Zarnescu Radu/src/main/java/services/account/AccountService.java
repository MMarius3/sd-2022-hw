package services.account;

import model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {
    List<Account> findAll() throws SQLException;

    Account findById(Long id);

    boolean save(Account account);

    boolean update(Account account);
}
