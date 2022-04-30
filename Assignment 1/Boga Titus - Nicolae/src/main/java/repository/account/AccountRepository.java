package repository.account;

import model.Account;
import model.Client;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    Optional<Account> findById(Long id);

    boolean save(Account account);

    boolean update(Account account);

    void deleteById(Long id);

    List<Account> findByClientId(Long id);

}
