package service.account;

import model.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id);

    boolean save(Account account);

    boolean saveAccount(Account account, Long clientId);

    boolean update(Account account);

    boolean delete(Long id);

    boolean transfer(Long id1, Long id2, Integer moneyAmountToTransfer);

    boolean bill(Long id, Integer billToExecute);

    boolean addAccount(String type, Integer moneyAmount, Long clientId);

    boolean updateAccount(Long id, String type, Integer moneyAmount);

    boolean deleteAccount(Long id);

    Account viewAccount(Long id);

    boolean transferMoney(Long id1, Long id2, Integer moneyAmountToTransfer);

    boolean executeBill(Long id, Integer billToExecute);

}