package controller;

import model.Role;
import model.User;
import model.validator.UserValidator;
import service.AuthenticationService;
import view.AdminView;
import view.LoginView;
import view.EmployeeView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {
    private final EmployeeView employeeView;
    private final LoginView loginView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator, EmployeeView employeeView, AdminView adminView) {
        this.loginView = loginView;
        this.adminView  = adminView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.employeeView = employeeView;
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.loginView.addRegisterAdminButtonListener(new RegisterAdminButtonListener());
        this.loginView.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            User user = authenticationService.login(username, password);
            loginView.setVisible(false);
            if(user.getRoles().get(0).getRole().equals("employee"))
                employeeView.setVisible(true);
            else
            if(user.getRoles().get(0).getRole().equals("administrator"))
                adminView.setVisible(true);

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
    private class RegisterAdminButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.registerAdmin(username, password);
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }
}
