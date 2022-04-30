package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount(long amount){
        account.setAmount(amount);
        return this;
    }

    public AccountBuilder setDateOfCreation(Date dateOfCreation){
        account.setDateOfCreation(dateOfCreation);
        return this;
    }

    public Account build(){
        return account;
    }
}
