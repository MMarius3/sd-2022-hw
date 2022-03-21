package model.builder;

import model.Account;
import model.Client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AccountBuilder {
    private Account account;
    public AccountBuilder(){account=new Account();}
    public AccountBuilder setType(String type)
    {
        account.setType(type);
        return this;
    }
    public AccountBuilder setMoney(Long money)
    {
        account.setMoney(money);
        return this;
    }
    public AccountBuilder setDateOfCreation(String dateOfCreation)
    {
        account.setDateOfCreation(dateOfCreation);
        return this;
    }
    public AccountBuilder setIdnumber(Long idnumber)
    {
        account.setIdnumber(idnumber);
        return this;
    }

    public AccountBuilder setClientID(int id)
    {
        account.setClientID(id);
        return this;
    }
    public Account build()
    {
        return account;
    }
}
