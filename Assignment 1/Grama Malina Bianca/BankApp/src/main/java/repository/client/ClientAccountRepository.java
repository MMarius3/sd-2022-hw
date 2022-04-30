package repository.client;

import model.Account;
import model.Client;

import java.util.List;

public interface ClientAccountRepository {
    void addAccountsToClient(Client client, List<Account> accounts);
    Account findAccountById(Long accountId);
    List<Account> findAccountsForClient(Long clientId);
    void addClientAccount(Long clientId, Long accountId);
    void removeAll();
}
