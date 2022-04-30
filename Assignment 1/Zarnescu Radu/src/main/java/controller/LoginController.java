package controller;

import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.Client.ClientRepositoryMySQL;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepositoryMySQL;
import services.account.AccountService;
import services.account.AccountServiceImpl;
import services.action.ActionService;
import services.action.ActionServiceImpl;
import services.client.ClientService;
import services.client.ClientServiceImpl;
import services.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginController {
    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;

    private final ClientService clientService;
    private final AccountService accountService;
    private final ActionService actionService;


    public LoginController(LoginView loginView, EmployeeView employeeView, AdminView adminView, AuthenticationService authenticationService, UserValidator userValidator, ClientService clientService, AccountService accountService, ActionService actionService) {
        this.loginView = loginView;
        this.employeeView = employeeView;
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.clientService = clientService;
        this.accountService = accountService;
        this.actionService = actionService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.loginView.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authenticationService.login(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Invalid username or password");
                return;
            }

            for (Role r : user.getRoles()) {
                if (r.getRole().equals("employee")) {
                    try {
                        new EmployeeController(employeeView, clientService, accountService, actionService, user);
                        employeeView.populateClientTable();
                        employeeView.populateAccountTable();
                        employeeView.setVisible(true);
                        loginView.setVisible(false);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    return;
                } else if (r.getRole().equals("administrator")) {
                    new AdminController(authenticationService, adminView, userValidator, actionService);
                    adminView.setVisible(true);
                    loginView.setVisible(false);
                    return;
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }
}
