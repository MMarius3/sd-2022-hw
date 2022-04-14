package service.TransferMoneyBetweenAccounts;

import model.Account;

public interface TransactionMaker {
    public void transfer(Account senderAccount, Account receiverAccount, float sum) throws Exception;
}
