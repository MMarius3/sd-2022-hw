package repository.account;

import controller.Response;
import model.Account;
import model.Client;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    Optional<Account> findById(Long id);

    boolean save(Account account);

    void removeAll();

    boolean remove(Account account);

    Response<Boolean> existsIdentificationNumber(String identificationNumber);

    boolean edit(Account account);

    boolean existsClientId(String clientId);

    boolean existsAccountId(String accountId);

    boolean validSum(float sum, Long from);

    void updateAmount(float sum, Long id);
}
