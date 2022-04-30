package controller;

import launcher.ComponentFactory;
import model.User;
import model.validation.Notification;
import service.authentication.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static database.Constants.Roles.ADMINISTRATOR;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ComponentFactory componentFactory;

    public LoginController(ComponentFactory componentFactory, LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.componentFactory = componentFactory;
        loginView.setLoginButtonListener(new LoginButtonListener());
        // loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                User user = loginNotification.getResult();
                if (user.getRoles().get(0).getRole().equals(ADMINISTRATOR)){
                    componentFactory.getAdminView().setVisible();
                    componentFactory.getLoginView().setVisible(false);
                } else {
                    componentFactory.getEmployeeView().setVisible();
                    componentFactory.getLoginView().setVisible(false);
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again " +
                            "later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }


}
