package repository.account;

import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> viewAccount(Integer id);

    boolean createAccount(Account account);

    boolean updateAccount(Account account);

    void deleteAccount(Integer id);

    Optional<Account> findByClientID(Integer id);

    List<Account> viewAccounts();
}
