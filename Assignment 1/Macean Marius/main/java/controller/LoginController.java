package controller;

import database.JDBConnectionWrapper;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.client_account.ClientAccountRepository;
import repository.client_account.ClientAccountRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
        this.loginView.setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            authenticationService.login(username, password);

            final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

            final ClientAccountRepository clientAccountRepository = new ClientAccountRepositoryMySQL(connection);

            final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
            final ClientService clientService = new ClientServiceImpl(clientRepository);

            final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
            final AccountService accountService = new AccountServiceImpl(accountRepository, clientAccountRepository);

            final ClientValidator clientValidator = new ClientValidator(clientRepository);
            final AccountValidator accountValidator = new AccountValidator(accountRepository, clientRepository);

            final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
            final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
            final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
            final UserValidator userValidator = new UserValidator(userRepository, authenticationService);

            if (authenticationService.isEmployee(username, password)) {
                final EmployeeView employeeView = new EmployeeView();
                new EmployeeController(employeeView, clientService, accountService, clientValidator, accountValidator);
            } else {
                final AdministratorView administratorView = new AdministratorView();
                new AdministratorController(administratorView, authenticationService, userValidator);
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