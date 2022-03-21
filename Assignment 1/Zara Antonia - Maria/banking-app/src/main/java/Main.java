import controller.LoginController;
import database.JDBConnectionWrapper;
import model.Activity;
import model.validator.ActivityValidator;
import model.validator.ClientAccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.client_account.ClientAccountRepository;
import repository.client_account.ClientAccountRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.client_account.ClientAccountService;
import service.client_account.ClientAccountServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final ClientAccountRepository clientAccountRepository = new ClientAccountRepositoryMySQL(connection, clientRepository);
        final ActivityRepository activityRepository = new ActivityRepositoryMySQL(connection, userRepository);

        final UserValidator userValidator = new UserValidator(userRepository);
        final ClientValidator clientValidator = new ClientValidator(clientRepository);
        final ClientAccountValidator clientAccountValidator = new ClientAccountValidator(clientAccountRepository);
        final ActivityValidator activityValidator = new ActivityValidator(userRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);
        final ClientService clientService = new ClientServiceImpl(clientRepository, clientValidator);
        final ClientAccountService clientAccountService = new ClientAccountServiceImpl(clientAccountRepository);
        final ActivityService activityService = new ActivityServiceMySQL(activityRepository);

        final LoginView loginView = new LoginView();

        new LoginController(loginView, authenticationService, userValidator,
                clientService, clientAccountService, activityService,
                clientValidator, clientAccountValidator, activityValidator);
    }
}