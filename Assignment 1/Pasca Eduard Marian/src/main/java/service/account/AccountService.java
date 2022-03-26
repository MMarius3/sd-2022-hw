package service.account;

import model.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    boolean save(Account account);

    boolean removeById(long id);

    Account update(Account account);
}
