package controller;

import model.User;
import model.validator.LoginValidator;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {
    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final LoginValidator loginValidator;

    public LoginController(LoginView loginView, EmployeeView employeeView, AdminView adminView, AuthenticationService authenticationService, UserValidator userValidator, LoginValidator loginValidator) {
        this.loginView = loginView;
        this.employeeView = employeeView;
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.loginValidator = loginValidator;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.loginView.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();



            User loggedInUser = authenticationService.login(username, password);
            loginValidator.validate(username,password,loggedInUser);
            final List<String> errors = loginValidator.getErrors();
            if(errors.isEmpty()){
                if(loggedInUser.getRoles().get(0).getRole().equals("employee")){
                    loginView.setVisible(false);
                    employeeView.setVisible(true);
                }else if(loggedInUser.getRoles().get(0).getRole().equals("administrator")){
                    loginView.setVisible(false);
                    adminView.setVisible(true);
                }
            }else{
                JOptionPane.showMessageDialog(loginView.getContentPane(),loginValidator.getFormattedErrors());
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
