package controller;

import model.Account;
import model.builder.AccountBuilder;
import repository.account.AccountRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import view.AddAccountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class AddAccountController {

    private final AddAccountView addAccountView;

    private final Connection connection;

    public AddAccountController(AddAccountView addAccountView, Connection connection){
        this.addAccountView = addAccountView;
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

            AccountService accountService = new AccountServiceMySQL(new AccountRepositoryMySQL(connection));
            accountService.save(account);
        }
    }
}
