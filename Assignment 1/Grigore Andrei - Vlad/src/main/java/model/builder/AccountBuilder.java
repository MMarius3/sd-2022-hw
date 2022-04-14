package model.builder;

import model.Account;

import java.time.LocalDate;
import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setClientId(String clientId){
        account.setClientId(clientId);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(Long balance){
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setDate(Date created_at){
        account.setDate(created_at);
        return this;
    }


    public Account build() {
        return account;
    }
}
