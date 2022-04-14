package service;

import model.Account;

public interface ClientAccountActions extends RegularUserClientInformationActions {

    long createAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Account account);

    Account getAccount();
}
