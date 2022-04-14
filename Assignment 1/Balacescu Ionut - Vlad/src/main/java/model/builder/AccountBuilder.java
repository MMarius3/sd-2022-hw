package model.builder;

import model.Account;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setIdNumber(long idNumber){
        account.setIdNumber(idNumber);
        return this;
    }

    public AccountBuilder setTypeAccount(String typeAccount){
        account.setTypeAccount(typeAccount);
        return this;
    }
    public AccountBuilder setAmountOfMoney(int amountOfMoney){
        account.setAmountOfMoney(amountOfMoney);
        return this;
    }
    public AccountBuilder setCreationDate(String creationDate){
        account.setCreationDate(creationDate);
        return this;
    }
    public Account build() {
        return account;
    }
}
