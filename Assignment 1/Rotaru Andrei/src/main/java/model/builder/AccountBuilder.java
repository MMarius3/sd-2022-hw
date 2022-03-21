package model.builder;

import model.Account;
import model.User;

import java.sql.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder (){
        account= new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(Double balance) {
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setDate(Date date) {
        account.setDate(date);
        return this;
    }


    public AccountBuilder setIdClient(Long idClient) {
        account.setIdClient(idClient);
        return this;
    }

    public Account build() {
        return account;
    }
}
