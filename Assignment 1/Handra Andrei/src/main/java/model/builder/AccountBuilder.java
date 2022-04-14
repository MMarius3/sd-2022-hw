package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setIdentificationNumber(Long identificationNumber){
        account.setIdentificationNumber(identificationNumber);
        return this;
    }
    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }
    public AccountBuilder setSumOfMoney(Float sum){
        account.setSumOfMoney(sum);
        return this;
    }
    public AccountBuilder setCreationDate(Date creationDate){
        account.setCreationDate(creationDate);
        return this;
    }

    public AccountBuilder setCardNumber(String cardNumber){
        account.setCardNumber(cardNumber);
        return this;
    }

    public Account build(){
        return account;
    }
}
