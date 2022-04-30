package controller;

import model.User;
import service.user.UserService;
import view.UpdateUserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UpdateUserController {
    private final UpdateUserView updateUserView;

    private final Connection connection;

    private final UserService userService;

    public UpdateUserController(UpdateUserView updateUserView, Connection connection, UserService userService) {
        this.updateUserView = updateUserView;
        this.userService = userService;
        this.updateUserView.updateUserButtonListener(new UpdateUserController.UpdateUserButtonListener());
        this.connection = connection;
    }

    private class UpdateUserButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            User user = new User();
            user.setId(updateUserView.getId());
            user.setUsername(updateUserView.getUserNameTextField());
            user.setPassword(updateUserView.getPasswordTextField());

            userService.save(user);
        }
    }
}
