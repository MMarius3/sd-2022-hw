package service.account;

import model.Account;
import model.Client;
import service.account.AccountUpdateFields;

import java.util.List;

public interface CRUDAccount {
    List<Account> viewAccounts();
    List<Account> viewAccountForClient(Long clientId);
    Account findById(Long id);
    boolean update(Long id, Account account);
    boolean addAccount(Account account);
    boolean updateAccount(Account account, Account updatedAccount, AccountUpdateFields fields);
    boolean deleteAccount(Account account);
    boolean delete(Long id);
    boolean openAccount(Client client, Account account);
    boolean transferMoney(Account from, Account to, Float amount);
    boolean extractMoney(Account from, Float amount);
}
