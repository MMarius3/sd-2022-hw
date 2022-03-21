package service.account;

import javafx.collections.ObservableList;
import model.Account;
import model.Client;

import java.util.List;

public interface AccountService {
    ObservableList<Account> findAll();

    Account findById(Long id);

    boolean save(Account account);

    boolean remove(Account account);

    boolean edit(Account account);

    void updateAmount(float amount, Long id);
}
