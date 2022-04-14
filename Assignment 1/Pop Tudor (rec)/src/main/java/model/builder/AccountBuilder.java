package model.builder;

import model.Account;
import view.AccountDTO;

import java.time.LocalDate;

public class AccountBuilder {
    private final Account account;

    //constructor
    public AccountBuilder(){
        account = new Account();
    }

    //add builder DTO
    public Account builderAccountDTO(AccountDTO ac) {

        Account account = new AccountBuilder()
                .setType(ac.getType())
                .setCreationDate(ac.getCreationDate())
                .setIdentificationNR(ac.getIdentificationNr())
                .setMoneyAmount(ac.getMoneyAmount())
                .build();

        return account;

    }


    public Account build() {
        return account;
    }


    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setIdentificationNR(Integer identificationNr){
        account.setIdentificationNr(identificationNr);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setMoneyAmount(Integer moneyAmount){
        account.setMoneyAmount(moneyAmount);
        return this;
    }

    public AccountBuilder setCreationDate(LocalDate creationDate) {
        account.setCreationDate(creationDate);
        return this;
    }




}