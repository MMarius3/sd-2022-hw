package repository.account;

import model.Account;
import repository.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Long, Account> {

  List<Account> findAccountsByClientId(Long clientId);
}
