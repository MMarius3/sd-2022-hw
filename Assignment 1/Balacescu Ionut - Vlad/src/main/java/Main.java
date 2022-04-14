import controller.AccountViewController;
import controller.AdminViewController;
import controller.EmployeeViewController;
import controller.LoginController;
import database.Boostraper;
import database.JDBConnectionWrapper;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.employee.EmployeeRepository;
import repository.employee.EmployeeRepositoryMySQL;
import repository.security.RoleRepositoryMySQL;
import repository.security.RolesRepository;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.action.ActionService;
import service.action.ActionServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AccountView;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;
import java.sql.SQLException;

import static database.Constants.Schemas.PRODUCTION;
import static database.Constants.Schemas.TEST;

public class Main {
  public static void main(String[] args) throws SQLException {
    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
    //final Connection connection = new JDBConnectionWrapper(TEST).getConnection();

    ///repo
    final RolesRepository rolesRepository = new RoleRepositoryMySQL(connection);
    final UserRepository userRepository = new UserRepositoryMySQL(connection, rolesRepository);
    final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
    final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
    final EmployeeRepository employeeRepository = new EmployeeRepositoryMySQL(connection, rolesRepository);
    final ActionRepository actionRepository = new ActionRepositoryMySQL(connection);

    ///service
    final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
            rolesRepository);
    final ClientService clientService = new ClientServiceMySQL(clientRepository);
    final AccountService accountService = new AccountServiceMySQL(accountRepository);
    final EmployeeService employeeService = new EmployeeServiceMySQL(employeeRepository);
    final ActionService actionService = new ActionServiceMySQL(actionRepository);

    new Boostraper().execute();
    ///view
    final LoginView loginView = new LoginView();
    final EmployeeView employeeView = new EmployeeView();
    final AccountView accountView = new AccountView();
    final AdminView adminView = new AdminView();
    //validator
    final UserValidator userValidator = new UserValidator(userRepository);
    final ClientValidator clientValidator = new ClientValidator(clientRepository);
    //controller
    new LoginController(loginView, authenticationService, userValidator,employeeView, adminView);
    new EmployeeViewController(employeeView,clientService, clientValidator, accountView, loginView, actionService);
    new AccountViewController(accountView,accountService, employeeView);
    new AdminViewController(adminView,employeeService, userValidator, actionService);

  }
}
