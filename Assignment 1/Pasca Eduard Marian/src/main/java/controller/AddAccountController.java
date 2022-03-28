package controller;

import model.Account;
import model.builder.AccountBuilder;
import service.account.AccountService;
import view.AddAccountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class AddAccountController {

    private final AddAccountView addAccountView;

    private final Connection connection;

    private final AccountService accountService;

    public AddAccountController(AddAccountView addAccountView, Connection connection, AccountService accountService){
        this.addAccountView = addAccountView;
        this.accountService = accountService;
        this.addAccountView.addAccountButtonListener(new AddAccountButtonListener());
        this.connection = connection;
    }

    private class AddAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            Account account = new AccountBuilder()
                    .setAmount(addAccountView.getAmountTextField())
                    .setType(addAccountView.getTypeTextField())
                    .setDateOfCreation(addAccountView.getDateTextField())
                    .build();

            accountService.save(account);
        }
    }
}
