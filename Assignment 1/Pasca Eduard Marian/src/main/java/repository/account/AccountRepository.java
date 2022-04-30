package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    boolean save(Account account);

    boolean removeById(long id);

    boolean update(Account account);

    boolean transferMoney(long senderId, long receiverId, long amount);
}
