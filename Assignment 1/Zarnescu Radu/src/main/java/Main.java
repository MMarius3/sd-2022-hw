import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import repository.Client.ClientRepository;
import repository.Client.ClientRepositoryMySQL;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.security.RoleRepository;
import repository.security.RoleRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import services.account.AccountService;
import services.account.AccountServiceImpl;
import services.action.ActionService;
import services.action.ActionServiceImpl;
import services.client.ClientService;
import services.client.ClientServiceImpl;
import services.user.AuthenticationService;
import services.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;
import java.sql.SQLException;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
    public static void main(String[] args) throws SQLException {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RoleRepository rightsRolesRepository = new RoleRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final ActionRepository actionRepository = new ActionRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);
        final ClientService clientService = new ClientServiceImpl(clientRepository);
        final AccountService accountService = new AccountServiceImpl(accountRepository);
        final ActionService actionService = new ActionServiceImpl(actionRepository);

        final LoginView loginView = new LoginView();
        final EmployeeView employeeView = new EmployeeView();
        final AdminView adminView = new AdminView();

        final UserValidator userValidator = new UserValidator(userRepository);

        new LoginController(loginView, employeeView, adminView, authenticationService, userValidator, clientService, accountService, actionService);


    }
}
