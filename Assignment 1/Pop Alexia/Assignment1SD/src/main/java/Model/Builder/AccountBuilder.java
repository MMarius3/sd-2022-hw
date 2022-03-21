package Model.Builder;

import Model.Account;
import java.sql.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setAccNr(Long nr) {
        account.setAccnr(nr);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }
    public AccountBuilder setAmount(int amount){
        account.setAmount(amount);
        return this;
    }
    public AccountBuilder setDate(Date date){
        account.setDateOfCreation(date);
        return this;
    }

    public Account build() {
        return account;
    }
}
