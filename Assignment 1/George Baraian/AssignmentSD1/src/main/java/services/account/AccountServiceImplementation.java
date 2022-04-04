package services.account;

import model.Account;
import model.validation.Notification;
import repositories.account.AccountRepository;

import java.util.List;

public class AccountServiceImplementation implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> save(Account account) {
       Account account1 = account;
       Notification<Boolean> accountNotification = new Notification<>();
       accountNotification.setResult(accountRepository.save(account1));

       return accountNotification;
    }

    @Override
    public void deleteByClientID(Long clientID) {
        accountRepository.deleteByClientId(clientID);
    }

    @Override
    public List<Account> findByClientId(Long clientId) {
        return accountRepository.findAllByClientId(clientId);
    }
}
