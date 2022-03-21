package service.account;

import model.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    Account findById(Long id);

    boolean save(Account account);

    boolean updateById(Long id, Account account);

    boolean removeById(Long id);

    boolean transferMoney(Long fromId, Long toId, Long transferAmount);

    boolean processBillsForId(Long id, Long billValue);
}
