package model.builder;

import model.Account;
import model.AccountType;
import model.Client;

import java.sql.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setId(Integer id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(AccountType type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(Integer balance) {
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setCreationDate(Date creationDate) {
        account.setCreationDate(creationDate);
        return this;
    }

    public AccountBuilder setClient(Client client){
        account.setClient(client);
        return this;
    }

    public Account build(){
        return account;
    }
}
