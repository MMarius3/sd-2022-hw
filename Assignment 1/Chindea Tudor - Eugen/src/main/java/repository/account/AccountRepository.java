package repository.account;

import model.Account;
import model.Client;

import java.time.LocalDate;
import java.util.List;

public interface AccountRepository {

    void addAccount(Account account);

    void deleteAccount(Long id);
    void payBill(Long id, Long amount);
    void transferMoney(Long idFrom, Long idTo, Long amount);
    void updateAccountBallance(Long id,Long ballance);
    Account findAccountById(Long id);

    List<Account> findAccountsForClient(Long clientid);
}
