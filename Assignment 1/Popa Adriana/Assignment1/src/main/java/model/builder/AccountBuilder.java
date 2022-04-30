package model.builder;

import model.Account;

public class AccountBuilder {
    private final Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setIdentificationNumber(String identificationNumber){
        account.setIdentificationNumber(identificationNumber);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmountOfMoney(String amountOfMoney){
        account.setAmountOfMoney(amountOfMoney);
        return this;
    }

    public AccountBuilder setDateOfCreation(String dateOfCreation){
        account.setDateOfCreation(dateOfCreation);
        return this;
    }

    public Account build(){
        return account;
    }

    public AccountBuilder setId(int id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setClientId(Integer id){
        account.setClientId(id);
        return this;
    }

}
