package model.builder;

import model.Account;

import java.sql.Date;

public class AccountBuilder {
    private Account account;

    private Long identificationNumber;
    private String type;
    private Double amount;
    private Date dateOfCreation;

    public AccountBuilder(){
        account=new Account();
    }

    public AccountBuilder setAccountId(Long identificationNumber){
        account.setAccountId(identificationNumber);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount(Double amount){
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
