package service.transfer;

import model.Account;
import repository.AccountRepository;

public class InterAccountsMoneyTransfer implements IInterAccountsMoneyTransfer {

    private AccountRepository accountRepository;

    @Override
    public void transfer(Account firstPartyAccount, Account secondPartyAccount, float amount) {

        firstPartyAccount.setBalance(firstPartyAccount.getBalance() - amount);
        secondPartyAccount.setBalance(secondPartyAccount.getBalance() + amount);

        accountRepository.update(firstPartyAccount);
        accountRepository.update(secondPartyAccount);
    }

    @Override
    public IInterAccountsMoneyTransfer setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        return this;
    }
}
