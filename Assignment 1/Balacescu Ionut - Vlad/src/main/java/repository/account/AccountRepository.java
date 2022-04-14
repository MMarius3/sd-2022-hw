package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    boolean addAccount(Account account,long clientId);

    void addAccountToClient(long idClient, long idAccount);

    void updateAccount(Account account);

    void deleteAccount(long id);

    List<Account> getAccountsForClient(long clientID);

    Account findAccountById(long id);

}
