package repository.account;

import controller.Response;
import model.Account;
import model.Action;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findByClient(String clientId);
    boolean save(Account account) throws SQLException;
    void removeAll();

    Response<Boolean> existsByClientId(Long clientId);
}
