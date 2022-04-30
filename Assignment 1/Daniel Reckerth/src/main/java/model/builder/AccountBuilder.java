package model.builder;

import model.Account;
import model.Client;
import model.enums.AccountType;

import java.time.LocalDate;
import java.util.Formatter;

public class AccountBuilder {

  private final Account account;

  public AccountBuilder() {
    this.account = new Account();
    this.account.setCreationDate(LocalDate.now());
  }

  public AccountBuilder setId(Long id) {
    account.setId(id);
    return this;
  }

  public AccountBuilder setAccountType(AccountType accountType) {
    account.setAccountType(accountType);
    return this;
  }

  public AccountBuilder setBalance(double balance) {
    Formatter formatter = new Formatter();
    formatter.format("%.2f", balance);
    account.setBalance(Double.parseDouble(formatter.toString()));
    return this;
  }

  public AccountBuilder setCreationDate(LocalDate creationDate) {
    account.setCreationDate(creationDate);
    return this;
  }

  public AccountBuilder setClient(Client client) {
    account.setClient(client);
    return this;
  }

  public Account build() {
    return account;
  }
}
