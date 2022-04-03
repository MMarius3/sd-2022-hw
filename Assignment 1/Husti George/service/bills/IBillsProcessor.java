package service.bills;

import repository.AccountRepository;

public interface IBillsProcessor {
    void processBills() throws Exception;
    IBillsProcessor setAccountRepository(AccountRepository accountRepository);
    IBillsProcessor setAccountID(int accountID);
    IBillsProcessor setSum(float sum);
}
