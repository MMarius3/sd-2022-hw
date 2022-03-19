package repository.account;

import model.Account;
import repository.Repository;

public interface AccountRepository extends Repository<Account, Long> {

    boolean transferMoney(Long id, int money);

    Account findByNumber(String number);
}
