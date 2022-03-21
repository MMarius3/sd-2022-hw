package model.builder;


import model.*;

import java.sql.Date;
import java.util.List;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setClientId(Long clientId) {
        account.setClientId(clientId);
        return this;
    }

    public AccountBuilder setIdentificationNumber(String identificationNumber) {
        account.setIdentificationNumber(identificationNumber);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(AccountType.fromString(type));
        return this;
    }
    public AccountBuilder setAmount(float amount){
        account.setAmount(amount);
        return this;
    }
    public AccountBuilder setDateOfCreation(Date dateOfCreation){
        account.setDateOfCreation(dateOfCreation);
        return this;
    }


    public Account build() {
        return account;
    }


}

