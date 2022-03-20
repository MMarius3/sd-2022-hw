package respository.account;

import controller.Response;
import model.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    List<Account> findByClientId(int id);

    Account findById(int id);

    boolean save(Account account);

    boolean removeAccount(Account account);

    boolean update(Account account);

}
