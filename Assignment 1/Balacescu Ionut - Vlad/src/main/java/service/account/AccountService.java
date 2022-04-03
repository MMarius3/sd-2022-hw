package service.account;

import model.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    boolean addAccount(Account account,long clientId);

    void updateAccount(Account account);

    List<Account> getAccountsForClient(long clientId);

    Account findAccountById(long id);

    void deleteAccount(long id);
}
