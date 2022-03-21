package model.builder;

import model.Account;
import model.Client;

import java.time.LocalDate;

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
    public AccountBuilder setBalance(Long balance) {
        account.setBalance(balance);
        return this;
    }
    public AccountBuilder setCreated_at(LocalDate date) {
        account.setCreated_at(date);
        return this;
    }
    public AccountBuilder setClient_id(Long client_id) {
        account.setClient_id(client_id);
        return this;
    }

    public Account build() {
        return account;
    }

}
