package model.builder;

import model.Account;
import model.User;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.sql.Timestamp;

public class AccountBuilder {

    private Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setClientId(int id){
        account.setClient_id(id);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount(Long amount){
        account.setAmount(amount);
        return this;
    }

    public AccountBuilder setId(int id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setDateOfCreation(Timestamp dateOfCreation){
        account.setCreated_at(dateOfCreation);
        return this;
    }

    public Account build() {
        return account;
    }
}
