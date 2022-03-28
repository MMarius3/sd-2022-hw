package controller;

import model.Account;
import model.builder.AccountBuilder;
import service.account.AccountService;
import service.user.UserService;
import view.UpdateAccountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UpdateAccountController {

    private final UpdateAccountView updateAccountView;

    private final Connection connection;

    private final AccountService accountService;

    public UpdateAccountController(UpdateAccountView updateAccountView, Connection connection, AccountService accountService) {
        this.updateAccountView = updateAccountView;
        this.connection = connection;
        this.accountService = accountService;
        this.updateAccountView.updateAccountButtonListener(new UpdateAccountButtonListener());
    }

    private class UpdateAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            Account account = new AccountBuilder()
                    .setAmount(updateAccountView.getAmountTextField())
                    .setId(updateAccountView.getId())
                    .setType(updateAccountView.getTypeTextField())
                    .build();

            accountService.update(account);
        }
    }
}
