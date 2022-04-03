package repository.Account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

   Account findById(Long id) throws EntityNotFoundException;// throws EntityNotFoundException;

   Account findByClientId(Long clientId) throws EntityNotFoundException;// throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();

    boolean remove(Long id);

    boolean update(Account account);

    void transfer(Account account1, Account account2, Integer amount) throws EntityNotFoundException;

}
