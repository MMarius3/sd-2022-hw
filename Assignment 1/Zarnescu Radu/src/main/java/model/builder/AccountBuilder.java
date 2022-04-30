package model.builder;

import model.Account;

import java.time.LocalDate;


public class AccountBuilder {

    private final Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setId(Long id) {
        this.account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        this.account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(Float balance) {
        this.account.setBalance(balance);
        return this;
    }

    public AccountBuilder setDateOfCreation(LocalDate dateOfCreation) {
        this.account.setDateOfCreation(dateOfCreation);
        return this;
    }

    public AccountBuilder setClientID(Long id) {
        this.account.setClientID(id);
        return this;
    }

    public Account build() {
        return this.account;
    }
}
