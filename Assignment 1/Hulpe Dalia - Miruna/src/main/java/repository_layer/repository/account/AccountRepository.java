package repository_layer.repository.account;

import bussiness_layer.models.Account;

import java.util.List;

public interface AccountRepository {

  boolean insert (Account account, String username);

  List<Account> getUserAccounts (String username);

  Account getById (Long id);

  boolean remove (Long id);

  boolean update (Long  id, Account newAccount);
}
