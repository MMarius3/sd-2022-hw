package repository.account;

import controller.Response;
import model.Account;
import model.Client;

import java.util.List;



public interface AccountRepository {

    List<Account> findAll();

    Account findByClientId(Long id);

    Account findById(Long id);

    boolean save(Account account);

    void removeAll();

    void removeAccount(Long account_id);

    void updateAccount(Long account_id, String account_type, Long new_balance);

    Response<Boolean> existsByid(Long id);

    Response<Boolean> existsByCustomerId(Long id);
}