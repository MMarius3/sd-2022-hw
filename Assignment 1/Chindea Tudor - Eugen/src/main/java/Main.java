import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RolesRepository;
import repository.security.RolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.AuthenticationService;
import service.AuthenticationServiceMySQL;
import view.AdminView;
import view.BillsView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RolesRepository rolesRepository = new RolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rolesRepository);

        final LoginView loginView = new LoginView();
        final BillsView billsView = new BillsView();
        final AdminView adminView = new AdminView();
        final EmployeeView employeeView = new EmployeeView();
        final UserValidator userValidator = new UserValidator(userRepository);
        final ClientValidator clientValidator = new ClientValidator(clientRepository);
        new LoginController(loginView, authenticationService, userValidator,employeeView, adminView);

        new EmployeeController(employeeView, clientValidator, clientRepository,accountRepository, billsView);
        new AdminController(adminView, userValidator, userRepository, rolesRepository, authenticationService);
    }
}