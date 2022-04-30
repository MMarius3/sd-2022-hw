package service.Account;

import model.Account;
import model.validation.AccountValidator;
import model.validation.Notification;
import repository.Account.AccountRepository;
import repository.EntityNotFoundException;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }



    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public Account findByClientId(Long clientId) throws EntityNotFoundException {
        return repository.findByClientId(clientId);
    }

    @Override
    public Notification<Boolean> save(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        } else
            accountNotification.setResult(repository.save(account));
        return accountNotification;
    }

    @Override
    public Notification<Boolean> update(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountNotification::addError);
            accountNotification.setResult(Boolean.FALSE);
        } else
            accountNotification.setResult(repository.update(account));
        return accountNotification;
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    @Override
    public boolean remove(Long id) {
        return repository.remove(id);
    }

    @Override
    public void transfer(Account account1, Account account2, Integer amount) throws EntityNotFoundException {
        repository.transfer(account1, account2, amount);
    }
}
