package service.client.account;

import model.Account;
import service.client.ClientService;

public interface AccountService extends ClientService<Account, Long> {

    boolean delete(Long id);
}
