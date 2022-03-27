package controller;

import view.DeleteAccountView;

import java.sql.Connection;

public class DeleteAccountController {
    private final DeleteAccountView deleteAccountView;
    private final Connection connection;

    public DeleteAccountController(DeleteAccountView deleteAccountView, Connection connection) {
        this.deleteAccountView = deleteAccountView;
        this.connection = connection;
    }
}
