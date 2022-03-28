package controller;

import service.account.AccountService;
import view.DeleteAccountView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DeleteAccountController {
    private final DeleteAccountView deleteAccountView;
    private final Connection connection;
    private final AccountService accountService;

    public DeleteAccountController(DeleteAccountView deleteAccountView, Connection connection, AccountService accountService) {
        this.deleteAccountView = deleteAccountView;
        this.connection = connection;
        this.accountService = accountService;
        this.deleteAccountView.deleteAccountButtonListener(new DeleteAccountButtonListener());
    }

    private class DeleteAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            accountService.removeById(deleteAccountView.getId());
        }
    }
}
