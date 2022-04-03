package service.transfer;

import model.Account;
import repository.AccountRepository;

public interface IInterAccountsMoneyTransfer {
    void transfer(Account firstPartyAccount, Account secondPartyAccount, float amount);
    IInterAccountsMoneyTransfer setAccountRepository(AccountRepository accountRepository);
}
