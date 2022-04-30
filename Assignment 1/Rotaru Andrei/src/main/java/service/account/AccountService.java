package service.account;

import model.Account;
import model.validation.Notification;

import java.util.List;

public interface AccountService {
    Notification<Boolean> save(Account account);

    Notification<Account> viewAccount(Long id);

    Notification<Boolean> updateAccount(Account account);

    List<Account> findAll();

    boolean deleteAccount(Account account);

    List<Account> findAccountsByClientId(Long id);

    Notification<Boolean> transferBetweenAccounts(Long firstId, Long secondId, Double sum);

    Notification<Boolean> payBill(Long accountId, Double sum);

    void removeAll();
}
