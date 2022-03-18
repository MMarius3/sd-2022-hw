package controller;

import model.User;
import model.validator.UserValidator;
import service.user.authentication.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final EmployeeController employeeController;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator,
                           EmployeeController employeeController) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.employeeController = employeeController;

        loginView.addRegisterButtonListener(new RegisterButtonListener());
        loginView.addLoginButtonListener(new LoginButtonListener());
        loginView.addShowPasswordListener(new ShowPasswordListener());
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsernameField().getText();
            String password = new String(loginView.getPasswordField().getPassword());

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()) {
                authenticationService.register(username, password);
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors(),
                        "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsernameField().getText();
            String password = new String(loginView.getPasswordField().getPassword());

            User user = authenticationService.login(username, password);

            if(user == null) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Incorrect username or password",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if(user.getUsername().equals("admin")){

            } else {
                loginView.setVisible(false);
                employeeController.setViewVisible();
            }
        }
    }

    private class ShowPasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isCheckBoxSelected = loginView.getShowPassword().isSelected();
            if(isCheckBoxSelected) {
                loginView.getPasswordField().setEchoChar((char) 0);
            } else {
                loginView.getPasswordField().setEchoChar('*');
            }
        }
    }
}
