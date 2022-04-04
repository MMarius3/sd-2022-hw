package services.account;

import model.Account;
import model.validation.Notification;

import java.util.List;

public interface AccountService {

    Notification<Boolean> save(Account account);

    void deleteByClientID(Long clientID);

    List<Account> findByClientId(Long clientId);
}
