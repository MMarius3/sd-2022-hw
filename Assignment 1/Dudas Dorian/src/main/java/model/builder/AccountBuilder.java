package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setClientId(Long clientId){
        account.setClientId(clientId);
        return this;
    }

    public AccountBuilder setAccountType(String accountType){
        account.setAccountType(accountType);
        return this;
    }

    public AccountBuilder setMoneyAmount(Long moneyAmount){
        account.setMoneyAmount(moneyAmount);
        return this;
    }

    public AccountBuilder setCreationDate(Date creationDate){
        account.setCreationDate(creationDate);
        return this;
    }

    public Account build(){
        return account;
    }
}
