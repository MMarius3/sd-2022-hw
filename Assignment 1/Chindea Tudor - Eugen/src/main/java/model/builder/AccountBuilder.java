package model.builder;

import model.Account;

import java.time.LocalDate;

public class AccountBuilder {

    private Account account;

    public AccountBuilder(){
        account = new Account();
    }
    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }
    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }
    public AccountBuilder setBallance(Long ballance){
        account.setBalance(ballance);
        return this;
    }

    public AccountBuilder setClientId(Long client_id){
        account.setClient_id(client_id);
        return this;
    }
    public  AccountBuilder setCreationdate(LocalDate date){
        account.setCreation(date);
        return  this;
    }
    public Account build(){return account;}
}
