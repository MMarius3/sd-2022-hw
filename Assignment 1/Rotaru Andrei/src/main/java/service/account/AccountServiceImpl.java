package service.account;

import model.Account;
import model.validation.AccountValidator;
import model.validation.BillValidator;
import model.validation.Notification;
import model.validation.TransferValidator;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> save(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> saveAccountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(saveAccountNotification::addError);
            saveAccountNotification.setResult(Boolean.FALSE);
        } else {
            saveAccountNotification.setResult(accountRepository.save(account));
        }
        return saveAccountNotification;
    }

    @Override
    public Notification<Account> viewAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Notification<Boolean> updateAccount(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> updateAccountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(updateAccountNotification::addError);
            updateAccountNotification.setResult(Boolean.FALSE);
        } else {
            updateAccountNotification.setResult(accountRepository.update(account));
        }
        return updateAccountNotification;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountRepository.delete(account);
    }

    @Override
    public List<Account> findAccountsByClientId(Long id) {
        return accountRepository.findAccountsByClientId(id);
    }

    @Override
    public Notification<Boolean> transferBetweenAccounts(Long id1, Long id2, Double sum) {
        Notification<Boolean> transferNotification = new Notification<>();
        Account account1 = accountRepository.findById(id1).getResult();
        Account account2 = accountRepository.findById(id2).getResult();
        Double balance1 = account1.getBalance();
        Double balance2 = account2.getBalance();

        TransferValidator transferValidator = new TransferValidator(account1, account2, sum);
        boolean transferValid = transferValidator.validate();

        if(!transferValid){
            transferValidator.getErrors().forEach(transferNotification::addError);
            transferNotification.setResult(Boolean.FALSE);
        }
        else{
            balance1 = balance1 - sum;
            balance2 = balance2 + sum;
            account1.setBalance(balance1);
            account2.setBalance(balance2);
            transferNotification.setResult(accountRepository.update(account1) && accountRepository.update(account2));
        }

        return transferNotification;
    }

    @Override
    public Notification<Boolean> payBill(Long id, Double sum) {
        Notification<Boolean> billNotification = new Notification<>();
        Account account = accountRepository.findById(id).getResult();
        Double balance = account.getBalance();

        BillValidator billValidator = new BillValidator(account,sum);
        boolean billValid = billValidator.validate();

        if(!billValid){
            billValidator.getErrors().forEach(billNotification::addError);
            billNotification.setResult(Boolean.FALSE);
        }
        else{
            balance = balance - sum;
            account.setBalance(balance);
            billNotification.setResult(accountRepository.update(account));
        }

        return billNotification;
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }
}
