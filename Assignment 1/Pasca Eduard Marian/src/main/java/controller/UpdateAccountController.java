package controller;

import view.UpdateAccountView;

import java.sql.Connection;

public class UpdateAccountController {
    private final UpdateAccountView updateAccountView;
    private final Connection connection;

    public UpdateAccountController(UpdateAccountView updateAccountView, Connection connection) {
        this.updateAccountView = updateAccountView;
        this.connection = connection;
    }
}
