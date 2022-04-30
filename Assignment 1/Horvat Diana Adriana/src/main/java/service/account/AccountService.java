package service.account;

import model.Account;

import java.util.List;

public interface AccountService {

    boolean addAccount(int client_id, String type, Long amount);

    Account findById(int id);

    List<Account> getAllAccounts();

    List<Account> findByClientId(int id);

    boolean removeAccount(Account account);

    boolean updateAccount(Account account);
}
