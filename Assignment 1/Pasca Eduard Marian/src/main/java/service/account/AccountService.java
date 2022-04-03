package service.account;

import model.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    boolean save(Account account);

    boolean removeById(long id);

    boolean update(Account account);

    boolean transferMoney(long senderId, long receiverId, long amount);
}
