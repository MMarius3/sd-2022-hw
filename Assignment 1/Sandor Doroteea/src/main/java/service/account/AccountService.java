package service.account;

import model.Account;
import model.Client;

import java.util.Date;
import java.util.List;

public interface AccountService {
    boolean add(Long balance, String type, Date dateOfCreation, Long client_id);
    boolean delete(Long id);
    List<Account> view();
    boolean update(Long id,Long balance, String type, Date dateOfCreation, Long client_id);
    Account viewAccount(Long id);
}
