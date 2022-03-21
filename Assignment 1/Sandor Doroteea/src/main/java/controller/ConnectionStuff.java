package controller;

import database.JDBConnectionWrapper;
import model.ActivityUser;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityUserRepository;
import repository.activity.ActivityUserRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.activity.ActivityUserService;
import service.activity.ActivityUserServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class ConnectionStuff {
    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    final ClientRepository clientRepository = new ClientRepositoryMySQL(connection,rightsRolesRepository);
    final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
    final ActivityUserRepository activityUserRepository = new ActivityUserRepositoryMySQL(connection);


    final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    final ClientService clientService = new ClientServiceMySQL(clientRepository);
    final AccountService accountService = new AccountServiceMySQL(accountRepository);
    final ActivityUserService activityUserService = new ActivityUserServiceMySQL(activityUserRepository);

    final UserValidator userValidator = new UserValidator(userRepository);
    final ClientValidator clientValidator = new ClientValidator(clientRepository);
    final AccountValidator accountValidator = new AccountValidator(accountRepository);

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public ClientService getClientService(){return  clientService;}

    public AccountService getAccountService(){return accountService;}

    public ActivityUserService getActivityUserService(){return  activityUserService;}

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public ClientValidator getClientValidator(){return clientValidator;}

    public AccountValidator getAccountValidator(){return accountValidator;}

    public String getCurrentUserUsername() {
        return currentUserUsername;
    }

    public void setCurrentUserUsername(String currentUserUsername) {
        ConnectionStuff.currentUserUsername = currentUserUsername;
    }

    public static String currentUserUsername;


}
