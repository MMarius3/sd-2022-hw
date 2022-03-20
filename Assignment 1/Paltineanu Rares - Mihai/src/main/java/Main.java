import controller.admin.AdminController;
import controller.employee.EmployeeController;
import controller.LoginController;
import database.Boostrap;
import database.JDBConnectionWrapper;
import model.Client;
import model.validator.AccountValidator;
import model.validator.ClientInformationValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.action.ActionService;
import service.action.ActionServiceMySQL;
import service.client.ClientService;
import service.client.account.AccountService;
import service.client.account.AccountServiceMySQL;
import service.client.information.InformationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import service.user.authentication.AuthenticationService;
import service.user.authentication.AuthenticationServiceMySQL;
import view.EmployeeView;
import view.LoginView;
import view.admin.AdminView;

import java.sql.Connection;
import java.sql.SQLException;

import static database.Constants.Schemas.PRODUCTION;

public class Main {

    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        final ActionRepository actionRepository = new ActionRepositoryMySQL(connection);

        try {
            new Boostrap(rightsRolesRepository);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);
        final LoginView loginView = new LoginView();

        final UserValidator userValidator = new UserValidator(userRepository);

        final ClientInformationValidator clientInformationValidator = new ClientInformationValidator(clientRepository);
        final AccountValidator accountValidator = new AccountValidator(clientRepository, accountRepository);

        ClientService<Client, Long> clientService = new InformationServiceMySQL(clientRepository);
        AccountService accountService = new AccountServiceMySQL(accountRepository);
        final ActionService actionService = new ActionServiceMySQL(actionRepository);

        final EmployeeController employeeController = new EmployeeController(new EmployeeView(),
                clientInformationValidator,
                accountValidator,
                clientService,
                accountService, actionService);
        final UserService userService = new UserServiceMySQL(userRepository, rightsRolesRepository);
        final AdminController adminController = new AdminController(new AdminView(), userValidator, userService, actionService, rightsRolesRepository);
        new LoginController(loginView, authenticationService, userValidator, employeeController, adminController);
    }
}
