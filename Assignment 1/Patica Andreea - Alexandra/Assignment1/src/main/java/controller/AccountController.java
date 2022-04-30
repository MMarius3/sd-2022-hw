package controller;

import model.Account;
import model.AccountType;
import model.Client;
import model.builder.AccountBuilder;
import model.validator.AccountValidator;
import repository.account.AccountRepository;
import service.account.AccountService;
import view.AccountView;
import view.MessageView;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class AccountController {
    private final AccountView accountView;
    private final AccountService accountService;
    private final AccountValidator accountValidator;

    public AccountController(AccountService accountService, AccountRepository accountRepository){
        this.accountView = new AccountView();
        this.accountService = accountService;
        this.accountValidator = new AccountValidator(accountRepository);
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
            String type = accountView.getTypeField();
            String balance = accountView.getBalanceField();

            accountValidator.validate(type, balance);

            final List<String> errors = accountValidator.getErrors();
            if (errors.isEmpty()) {
                Account account = new AccountBuilder()
                        .setType(new AccountType(Long.parseLong("1"), accountView.getTypeField())) //TODO checkbox
                        .setBalance(Integer.valueOf(accountView.getBalanceField()))
                        .setCreationDate(new Date(Calendar.getInstance().getTime().getTime()))
                        .setClient(client)
                        .build();
                accountService.save(account);
                new MessageView().display("Account saved successfully");
            } else {
                new MessageView().display( accountValidator.getFormattedErrors());
                //JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }


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
