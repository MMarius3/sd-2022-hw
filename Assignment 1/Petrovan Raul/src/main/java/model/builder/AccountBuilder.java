package model.builder;

import model.Account;
import model.AccountTypes;
import model.Role;
import model.User;

import java.util.Date;
import java.util.List;

public class AccountBuilder {


    private final Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(AccountTypes type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(float balance) {
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setCreationDate(Date creationDate) {
        account.setCreationDate(creationDate);
        return this;
    }

    public AccountBuilder setAccountNumber(String accountNumber) {
        account.setAccountNumber(accountNumber);
        return this;
    }



    public Account build() {
        return account;
    }


}
