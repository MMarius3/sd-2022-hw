package service.account;

import model.Account;
import service.Service;

import java.util.List;

public interface AccountService extends Service<Long, Account> {

  List<Account> findAccountsByClientId(Long clientId);
}
