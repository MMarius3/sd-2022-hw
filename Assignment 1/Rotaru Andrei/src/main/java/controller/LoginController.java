package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;

public class LoginController extends SessionController{
    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdminView adminView;

    private final AuthenticationService authenticationService;

    public LoginController(LoginView loginView, EmployeeView employeeView, AdminView adminView,
                           AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.employeeView = employeeView;
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
        setLoggedInUser(-1L);
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

                setLoggedInUser(loginNotification.getResult().getId());

                boolean admin = false;
                for(Role r : loginNotification.getResult().getRoles()){
                    if(r.getRole().equals(ADMINISTRATOR)){
                        admin = true;
                    }
                }

                if(admin){
                    loginView.setVisible(false);
                    adminView.setVisible(true);
                }
                else{
                    loginView.setVisible(false);
                    employeeView.setVisible(true);
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
