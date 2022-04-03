package service.bills;

import model.Account;
import repository.AccountRepository;

import java.util.Optional;

public class UtilitiesBillsProcessor implements IBillsProcessor {

    private AccountRepository accountRepository;
    private int accountID;
    private float sum;

    @Override
    public void processBills() throws Exception {
        Optional<Account> account = accountRepository.getByID(accountID);

        if (account.isEmpty()) {
            throw new Exception("Account not found");
        }

        account.get().setBalance(account.get().getBalance() - sum);
        accountRepository.update(account.get());
    }

    @Override
    public IBillsProcessor setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        return this;
    }

    @Override
    public IBillsProcessor setAccountID(int accountID) {
        this.accountID = accountID;
        return this;
    }

    @Override
    public IBillsProcessor setSum(float sum) {
        this.sum = sum;
        return this;
    }
}
