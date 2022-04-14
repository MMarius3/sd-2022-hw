package controller;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import service.user.CRUDUser;
import view.admin.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class AdminController {

    private final AddUserView addUserView;
    private final EditUserView editUserView;
    private final DeleteUserView deleteUserView;
    private final DisplayUserView displayUserView;

    private final CRUDUser crudUser;

    public AdminController(AdminView adminView, AddUserView addUserView, EditUserView editUserView,
                           DeleteUserView deleteUserView, DisplayUserView displayUserView, CRUDUser crudUser) {
        this.crudUser = crudUser;

        adminView.setAddUserBtnListener(new DisplayAddUser());
        adminView.setEditUserBtnListener(new DisplayEditUser());
        adminView.setDeleteUserBtnListener(new DisplayDeleteUser());
        adminView.setViewUserBtnListener(new DisplayViewUser());

        this.addUserView = addUserView;
        this.editUserView = editUserView;
        this.deleteUserView = deleteUserView;
        this.displayUserView = displayUserView;

        this.addUserView.setAddUserBtnListener(new AddUserListener());

        this.editUserView.setSearchUserBtnListener(new EditUserSearchListener());
        this.editUserView.setUpdateUserBtnListener(new EditUserUpdateListener());

        this.deleteUserView.setSearchUserBtnListener(new DeleteUserSearchListener());
        this.deleteUserView.setUpdateUserBtnListener(new DeleteUserUpdateListener());

    }

    private class DisplayAddUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addUserView.setVisible();
        }
    }

    private class DisplayEditUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editUserView.setVisible();
        }
    }

    private class DisplayDeleteUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteUserView.setVisible();
        }
    }

    private class DisplayViewUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> users = crudUser.findAll();
            displayUserView.initializeTable(users);
        }
    }

    private class AddUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = addUserView.getUsername();
            String password = addUserView.getPassword();

            User user = new UserBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();


            Notification<Boolean> registerNotification = crudUser.save(user);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(addUserView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(addUserView.getContentPane(), "Adding a user not successful, please try again " +
                            "later.");
                } else {
                    JOptionPane.showMessageDialog(addUserView.getContentPane(), "Adding a user successful!");
                }
            }
        }
    }

    private class EditUserSearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(editUserView.getId());
            User user = crudUser.findByID(id);
            if (user != null) {
                editUserView.setUsername(user.getUsername());
                editUserView.setPassword(user.getPassword());
            } else {
                JOptionPane.showMessageDialog(editUserView.getContentPane(), "User not found.");
            }
        }
    }

    private class EditUserUpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(editUserView.getId());
            User user = crudUser.findByID(id);
            if (user != null) {
                if (Objects.equals(user.getPassword(), editUserView.getPassword())) {

                    Notification<Boolean> registerNotification = crudUser.updateUsername(id, editUserView.getUsername(), user.getPassword());

                    if (registerNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(editUserView.getContentPane(), registerNotification.getFormattedErrors());
                    } else {
                        if (!registerNotification.getResult()) {
                            JOptionPane.showMessageDialog(editUserView.getContentPane(), "Update not successful, please try again " +
                                    "later.");
                        } else {
                            JOptionPane.showMessageDialog(editUserView.getContentPane(), "Update successful!");
                        }
                    }
                } else if (Objects.equals(user.getUsername(), editUserView.getUsername())){
                    Notification<Boolean> registerNotification = crudUser.updatePassword(id, user.getUsername(), editUserView.getPassword());

                    if (registerNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(editUserView.getContentPane(), registerNotification.getFormattedErrors());
                    } else {
                        if (!registerNotification.getResult()) {
                            JOptionPane.showMessageDialog(editUserView.getContentPane(), "Update not successful, please try again " +
                                    "later.");
                        } else {
                            JOptionPane.showMessageDialog(editUserView.getContentPane(), "Update successful!");
                        }
                    }
                } else {
                    Notification<Boolean> registerNotification = crudUser.update(id, editUserView.getUsername(), editUserView.getPassword());

                    if (registerNotification.hasErrors()) {
                        JOptionPane.showMessageDialog(editUserView.getContentPane(), registerNotification.getFormattedErrors());
                    } else {
                        if (!registerNotification.getResult()) {
                            JOptionPane.showMessageDialog(editUserView.getContentPane(), "Update not successful, please try again " +
                                    "later.");
                        } else {
                            JOptionPane.showMessageDialog(editUserView.getContentPane(), "Update successful!");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(editUserView.getContentPane(), "User not found.");
            }
        }
    }

    private class DeleteUserSearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(deleteUserView.getId());
            User user = crudUser.findByID(id);
            if (user != null) {
                deleteUserView.setUsername(user.getUsername());
                deleteUserView.setPassword(user.getPassword());
            } else {
                JOptionPane.showMessageDialog(deleteUserView.getContentPane(), "User not found.");
            }
        }
    }

    private class DeleteUserUpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.valueOf(deleteUserView.getId());
            if (crudUser.delete(id)) {
                JOptionPane.showMessageDialog(deleteUserView.getContentPane(), "User deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(deleteUserView.getContentPane(), "There was an error in deleting the user. Please try again.");
            }
        }
    }
}

