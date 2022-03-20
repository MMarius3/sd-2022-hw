package repository.account;

import model.Account;

import java.sql.Date;
import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findByID(Long id);
    long save(Account account);
    boolean updateType(Account account, String type);
    boolean updateAmount(Account account, Float amount);
    boolean updateDate(Account account, Date date);
    boolean update(Long id, Account account);
    boolean delete(Account account);
    void removeAll();
}
