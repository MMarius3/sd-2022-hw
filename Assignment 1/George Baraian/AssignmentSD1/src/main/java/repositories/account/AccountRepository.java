package repositories.account;

import model.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAllByClientId(Long clientId);

    boolean save(Account account);

    void deleteByClientId(Long clientId);

    void removeAll();

    List<Account> findAll();

    //void updateAccountInformation();

}
