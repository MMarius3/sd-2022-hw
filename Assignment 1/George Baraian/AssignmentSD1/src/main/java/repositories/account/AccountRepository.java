package repositories.account;

import model.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAllByClientId();

    boolean save(Account account);

    void deleteByClientId(Long clientId);

    //void updateAccountInformation();

}
