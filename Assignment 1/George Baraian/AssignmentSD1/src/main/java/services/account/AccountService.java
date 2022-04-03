package services.account;

import model.Account;
import model.validation.Notification;

public interface AccountService {

    Notification<Boolean> save(Account account);

    void deleteByClientID(Long clientID);
}
