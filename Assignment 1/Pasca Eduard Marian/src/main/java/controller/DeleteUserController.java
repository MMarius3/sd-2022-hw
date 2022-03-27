package controller;

import view.DeleteUserView;

import java.sql.Connection;

public class DeleteUserController {
    private final DeleteUserView deleteUserView;
    private final Connection connection;

    public DeleteUserController(DeleteUserView deleteUserView, Connection connection) {
        this.deleteUserView = deleteUserView;
        this.connection = connection;
    }

}
