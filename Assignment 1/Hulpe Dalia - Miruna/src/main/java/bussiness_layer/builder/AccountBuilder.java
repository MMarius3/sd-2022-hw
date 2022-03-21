package bussiness_layer.builder;

import bussiness_layer.models.Account;

import java.sql.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmountOfMoney(Float money) {
        account.setAmount_of_money(money);
        return this;
    }

    public AccountBuilder setDateOfCreation(Date date) {
        account.setDate_of_creation(date);
        return this;
    }

    public AccountBuilder setUserId(Long id) {
        account.setUser_id(id);
        return this;
    }

    public Account build() {
        return account;
    }
}
