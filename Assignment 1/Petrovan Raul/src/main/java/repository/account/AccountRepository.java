package repository.account;

import model.Account;
import model.Bill;
import helpers.validation.Notification;

public interface AccountRepository {
    Notification<Account> addAccountToClient(Account a, Long clientId);

    Notification<Boolean> updateAccount(Account a);

    Notification<Boolean> deleteAccount(Long accountId);

    Notification<Boolean> transferMoney(String from_account, String to_account, float value);

    Notification<Boolean> payBill(Long from_account, Bill bill);
}
