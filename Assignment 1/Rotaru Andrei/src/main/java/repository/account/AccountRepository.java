package repository.account;

import model.Account;
import model.validation.Notification;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    Notification<Account> findById(Long id);

    boolean save(Account account);

    boolean update(Account account);

    boolean delete(Account account);

    List<Account> findAccountsByClientId(Long id);

    void removeAll();
}
