package controller;

import model.Account;
import model.AccountType;
import model.Client;
import model.builder.AccountBuilder;
import service.account.AccountService;
import view.AccountView;

import java.sql.Date;
import java.util.Calendar;

public class AccountController {
    private final AccountView accountView;
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountView = new AccountView();
        this.accountService = accountService;
    }

    public void startController(Account account, Client client){
        accountView.initializeFields();
        if(account != null){
            setFields(account);
            initializePayBillButtonListener(client, account);
            initializeDeleteButtonListener(account);
        }
        initializeSaveButtonListener(client);

        accountView.display();
    }

    private void setFields(Account account){
        accountView.setIdField(account.getId().toString());
        accountView.setTypeField(account.getType().getType());
        accountView.setBalanceField(account.getBalance().toString());
        accountView.setCreationDateField(account.getCreationDate().toString());
    }

    private void initializeSaveButtonListener(Client client){
        accountView.getSaveButton().setOnAction(e -> {
            Account account = new AccountBuilder()
                    .setType(new AccountType(Long.parseLong("1"), accountView.getTypeField())) //TODO checkbox
                    .setBalance(Integer.valueOf(accountView.getBalanceField()))
                    .setCreationDate(new Date(Calendar.DATE))
                    .setClient(client)
                    .build();
            accountService.save(account);
        });
    }

    private void initializeDeleteButtonListener(Account account){
        accountView.getDeleteButton().setOnAction(e -> {
            accountService.delete(account);
        });
    }

    private void initializePayBillButtonListener(Client client, Account account) {
        accountView.getPayBillButton().setOnAction(e -> {
            accountService.updateBalance(account, (Long.parseLong(account.getBalance().toString()) - Long.parseLong(accountView.getPayBillField())));
        });
    }
}
