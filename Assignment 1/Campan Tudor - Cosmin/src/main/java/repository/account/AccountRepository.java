package repository.account;

import model.Account;

import java.util.Optional;

public interface AccountRepository {
    Account findByIdnumber(Long idnumber);
    boolean save(Account account);
    void deleteByIdnumber(Long idnumber);
    void updateByIdnumber(Long idnumber,Account a2);

}
