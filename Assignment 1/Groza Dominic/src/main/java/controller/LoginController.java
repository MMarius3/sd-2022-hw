package controller;

import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.security.RolesRepository;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.AccountView;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final EmployeeView employeeView;
    private final AdminView adminView;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator
            , RolesRepository rolesRepository, UserRepository userRepository
            , ClientRepository clientRepository, AccountRepository accountRepository
            , EmployeeView employeeView, AdminView adminView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.employeeView = employeeView;
        this.adminView = adminView;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.loginView.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User loggedUser = authenticationService.login(username, password);
            User currentUser = userRepository.findByUsernameAndPassword(loggedUser.getUsername(), loggedUser.getPassword());

            Role userRole = rolesRepository.findRoleForUser(currentUser.getId());

            if (userRole.getId() == 2) {
                loginView.setVisible(false);
                employeeView.setVisible(true);
            } else {
                adminView.setVisible(true);
                loginView.setVisible(false);
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
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Successfully registered");

            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), userValidator.getFormattedErrors());
            }
        }
    }


}