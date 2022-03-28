package controller;

import service.user.UserService;
import view.DeleteUserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DeleteUserController {

    private final DeleteUserView deleteUserView;

    private final Connection connection;

    private final UserService userService;

    public DeleteUserController(DeleteUserView deleteUserView, Connection connection, UserService userService) {
        this.deleteUserView = deleteUserView;
        this.connection = connection;
        this.userService = userService;
        this.deleteUserView.deleteAccountButtonListener(new DeleteUserButtonListener());
    }

    private class DeleteUserButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            userService.removeById(deleteUserView.getId());
        }
    }

}
