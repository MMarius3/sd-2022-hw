package service.banking;

import helpers.validation.AccountValidator;
import helpers.validation.ClientValidator;
import lombok.RequiredArgsConstructor;
import model.Account;
import model.Bill;
import helpers.validation.Notification;
import model.Client;
import repository.account.AccountRepository;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public Notification<Account> addAccountToClient(Account a, Long clientId) {
        AccountValidator accountValidator = new AccountValidator(a);
        boolean accountValid = accountValidator.validate();
        Notification<Account> accountAddNotification = new Notification<>();

        if(!accountValid) {
            accountValidator.getErrors().forEach(accountAddNotification::addError);
        } else {
            return accountRepository.addAccountToClient(a, clientId);
        }
        return accountAddNotification;
    }

    @Override
    public Notification<Boolean> updateAccount(Account a) {
        AccountValidator accountValidator = new AccountValidator(a);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountUpdateNotification = new Notification<>();

        if(!accountValid) {
            accountValidator.getErrors().forEach(accountUpdateNotification::addError);
            accountUpdateNotification.setResult(Boolean.FALSE);
        } else {
            return accountRepository.updateAccount(a);
        }
        return accountUpdateNotification;
    }

    @Override
    public Notification<Boolean> deleteAccount(Long accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public Notification<Boolean> transferMoney(String from_account, String to_account, float value) {
        return accountRepository.transferMoney(from_account, to_account, value);
    }

    @Override
    public Notification<Boolean> payBill(Long from_account, Bill bill) {
        return accountRepository.payBill(from_account, bill);
    }
}
