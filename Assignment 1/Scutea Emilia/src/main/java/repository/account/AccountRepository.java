package repository.account;

import controller.Response;
import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    Account findById(Long id);

    Response<Boolean> existsById(Long id);

    boolean save(Account account);

    boolean update(Account account);

    void removeAll();

    Double selectAmount(Long id);
}
