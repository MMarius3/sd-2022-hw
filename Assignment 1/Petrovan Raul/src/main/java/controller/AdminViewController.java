package controller;

import helpers.Helpers;
import lombok.Getter;
import lombok.Setter;
import model.User;
import helpers.validation.Notification;
import service.logger.LoggerService;
import service.user.ClientService;
import view.AdminView;
import view.ReportView;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class AdminViewController {

    private final AdminView adminView;
    private final ClientService clientService;
    private final LoggerService loggerService;

    private User loggedUser;
    private List<User> users;
    private User selectedUser;

    private ReportView reportView;

    public AdminViewController(AdminView adminView, ClientService clientService,
                               LoggerService loggerService) {
        this.adminView = adminView;
        this.clientService = clientService;
        this.loggerService = loggerService;
        loadUserData();

        adminView.getUserTable().getSelectionModel().addListSelectionListener(new UserTableSelectionListener());

        adminView.getAddRoleButton().addActionListener(new AddRoleListener());
        adminView.getDeleteRoleButton().addActionListener(new DeleteRoleListener());
        adminView.getDeleteAccountButton().addActionListener(new DeleteAccountListener());

        adminView.getGenerateReportButton().addActionListener(new GenerateReportListener());
    }

    private void loadUserData() {
        Notification<List<User>> userFetchNotification = clientService.fetchUsersWithoutAccounts();
        if(userFetchNotification.hasErrors()) {
            JOptionPane.showMessageDialog(adminView.getContentPane(),
                    userFetchNotification.getFormattedErrors());
        } else {
            users = clientService.fetchUsersWithoutAccounts().getResult();
            adminView.setUsers(users);
        }
    }

    private class UserTableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
//            System.out.println(userView.getUsersTable().getSelectedRow());
            int selectedUserIndex = adminView.getUserTable().getSelectedRow();
            if (selectedUserIndex != -1) {
                selectedUser = users.get(selectedUserIndex);
                setSelectedUser();
            }
        }
    }

    private void setSelectedUser() {
        adminView.getEmail().setText(selectedUser.getUsername());
    }

    private class DeleteAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> deleteNotification =
                    clientService.deleteUser(selectedUser.getId());
            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(),
                        deleteNotification.getFormattedErrors());
            } else {
                users = Helpers.removeUserById(users, selectedUser.getId());
                loggerService.removeUser(loggedUser.getId(), selectedUser);
                selectedUser = null;
                adminView.setUsers(users);
            }
        }
    }

    private class AddRoleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> updateNotification =
                    clientService.addUserRole(selectedUser.getId(),
                            Objects.requireNonNull(adminView.getRoleComboBox().getSelectedItem()).toString());
            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(),
                        updateNotification.getFormattedErrors());
            } else {
                loadUserData();
                loggerService.addRole(loggedUser.getId(), selectedUser,
                        adminView.getRoleComboBox().getSelectedItem().toString());
            }
        }
    }

    private class DeleteRoleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> updateNotification =
                    clientService.removeUserRole(selectedUser.getId(),
                            Objects.requireNonNull(adminView.getRoleComboBox().getSelectedItem()).toString());
            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(),
                        updateNotification.getFormattedErrors());
            } else {
                loadUserData();
                loggerService.removeRole(loggedUser.getId(), selectedUser,
                        adminView.getRoleComboBox().getSelectedItem().toString());
            }
        }
    }

    private boolean ensureUserSelected() {
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(adminView.getContentPane(), "No user " +
                    "selected");
            return true;
        }
        return false;
    }

    private class GenerateReportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(ensureUserSelected()) return;
            reportView = new ReportView("Report generator");
            reportView.setVisible(true);
            reportView.getGenerateReportButton().addActionListener(new GenerateReportButtonListener());
        }
    }

    private class GenerateReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
