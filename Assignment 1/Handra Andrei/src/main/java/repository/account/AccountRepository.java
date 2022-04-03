package repository.account;

import controller.Response;
import model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findAccountByIdentificationNumber(Long identificationNumber);

    Optional<Account> findAccountByCardNumber(String cardNumber);

    boolean save(Account account);

    void removeAll();

    boolean update(Account account, String oldCardNumber);

    boolean delete(String cardNumber);

    Response<Boolean> existsByCardNumber(String cardNumber);
}
