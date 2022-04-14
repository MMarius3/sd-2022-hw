package repository.account;

import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findById(Long id);

    boolean save(Account account);

    Long lastId();

    boolean update(Account account);

    boolean delete(Long id);

    void removeAll();
}
