package controller;

import model.Activity;
import model.Role;
import model.User;
import model.validator.ActivityValidator;
import model.validator.ClientAccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import service.activity.ActivityService;
import service.client.ClientService;
import service.client_account.ClientAccountService;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static database.Constants.Roles.*;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final ClientAccountService clientAccountService;
    private final ActivityService activityService;
    private final UserValidator userValidator;
    private final ClientValidator clientValidator;
    private final ClientAccountValidator clientAccountValidator;
    private final ActivityValidator activityValidator;

    private EmployeeView employeeView;
    private AdministratorView administratorView;


    public LoginController(LoginView loginView, AuthenticationService authenticationService,
                           UserValidator userValidator, ClientService clientService,
                           ClientAccountService clientAccountService, ActivityService activityService,
                           ClientValidator clientValidator, ClientAccountValidator clientAccountValidator,
                           ActivityValidator activityValidator) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.clientService = clientService;
        this.clientAccountService = clientAccountService;
        this.activityService = activityService;
        this.clientValidator = clientValidator;
        this.clientAccountValidator = clientAccountValidator;
        this.activityValidator = activityValidator;

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

            try {
                if(user == null) {
                    throw new Exception("No user found");
                }

                System.out.println(user.getRoles().get(0).getRole());
                if(isOfRole(user,ADMINISTRATOR)){
                    administratorView = new AdministratorView();
                    AdministratorController administratorController = new AdministratorController(administratorView,
                            authenticationService, activityService, activityValidator, userValidator);
                }
                else if(isOfRole(user,EMPLOYEE)){
                    employeeView = new EmployeeView();
                    EmployeeController employeeController = new EmployeeController(employeeView, clientService,
                            clientAccountService, user, activityService, clientValidator, clientAccountValidator);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private boolean isOfRole(User user, String role) {
        for(Role r: user.getRoles()){
            if(r.getRole().equals(role)){
                return true;
            }
        }
        return false;
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