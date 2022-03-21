package controller;

import model.Action;
import model.User;
import model.validator.DateValidator;
import model.validator.UserValidator;
import services.action.ActionService;
import services.user.AuthenticationService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdminController {
    private List<User> users;
    private final AuthenticationService authenticationService;
    private final AdminView adminView;
    private final UserValidator userValidator;
    private final ActionService actionService;

    public AdminController(AuthenticationService authenticationService, AdminView adminView, UserValidator userValidator, ActionService actionService) {
        this.authenticationService = authenticationService;
        this.adminView = adminView;
        this.userValidator = userValidator;
        this.actionService = actionService;
        this.adminView.addAddButtonActionListener(new AddButtonActionListener());
        this.adminView.addDeleteActionListener(new DeleteButtonActionListener());
        this.adminView.addUpdateActionListener(new UpdateButtonActionListener());
        this.adminView.addGenerateButtonActionListener(new GenerateButtonActionListener());
        setUsers();
        this.adminView.populateTable();
    }

    private void setUsers() {
        List<User> users = authenticationService.getEmployees();
        this.adminView.setUsers(users);
        this.users = users;
    }

    private void refreshTable() throws SQLException {
        setUsers();
        adminView.populateTable();
    }

    private class AddButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Fields cannot be empty");
                return;
            }
            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
                try {
                    refreshTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(adminView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }

    private class DeleteButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            Optional<User> user = users.stream()
                    .filter(x -> x.getUsername().equals(username))
                    .findFirst();

            if (user.isEmpty()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Cannot find user");
                return;
            }

            authenticationService.deleteUser(user.get().getId());
            try {
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class UpdateButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getSelectionUsername();
            String password = adminView.getSelectionPassword();

            Long id = adminView.getSelectionID();

            Optional<User> user = users.stream()
                    .filter(x -> x.getId().equals(id))
                    .findFirst();

            if (user.isEmpty()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Error updating client");
                return;
            }

            if (user.get().getUsername().equals(username)) {
                userValidator.validatePassword(password);
            } else if (user.get().getPassword().equals(password)) {
                userValidator.validateUsername(username);
            } else {
                userValidator.validate(username, password);
            }
            final List<String> errors = userValidator.getErrors();

            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), userValidator.getFormattedErrors());
                return;
            }

            User updatedUser = user.get();
            updatedUser.setUsername(username);
            updatedUser.setPassword(password);

            authenticationService.updateUser(updatedUser);
            try {
                refreshTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class GenerateButtonActionListener implements ActionListener {

        static long reportNr = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            String d1 = adminView.getFromDate();
            String d2 = adminView.getToDate();
            Long id = Long.parseLong(adminView.getEmployeeID());

            DateValidator dateValidator = new DateValidator();

            dateValidator.validateDates(d1, d2);
            final List<String> errors = userValidator.getErrors();
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), userValidator.getFormattedErrors());
                return;
            }
            List<Action> actions = actionService.findActionsBetweenDates(d1, d2, id);

            String fileName = "report" + reportNr + ".txt";
            reportNr++;
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(fileName);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            for (Action action : actions) {
                String str = getStringFromAction(action);
                writer.println(str);
            }

            writer.close();
        }
    }

    private String getStringFromAction(Action action) {
        String s = "";
        s += action.getDate().toString();
        s += ": ";
        s += action.getType();
        s += " -> ";
        s += action.getDetails();

        return s;
    }
}
