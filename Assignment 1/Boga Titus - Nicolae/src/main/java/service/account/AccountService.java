package service.account;

import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id);

    boolean save(Account account);

    boolean update(Account account);

    void deleteById(Long id);

   List<Account> findByClientId(Long id);
}
