import controller.AdminController;
import controller.EmployeeController;
import controller.EmployeeManagerController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.ClientValidator;
import model.validator.DateValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.employeeActivity.EmployeeActivityRepository;
import repository.employeeActivity.EmployeeActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.employeeActivity.EmployeeActivityService;
import service.employeeActivity.EmployeeActivityServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserInfoService;
import service.user.UserInfoServiceImpl;
import view.AdminView;
import view.EmployeeManagerView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {

  public static void main(String[] args) {
    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

    final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
        rightsRolesRepository);

    final LoginView loginView = new LoginView();

    final AdminView adminView = new AdminView();

    final EmployeeView employeeView = new EmployeeView();

    final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
    final ClientService clientService = new ClientServiceImpl(clientRepository);

    final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
    final AccountService accountService = new AccountServiceImpl(accountRepository);

    final EmployeeActivityRepository employeeActivityRepository = new EmployeeActivityRepositoryMySQL(connection);
    final EmployeeActivityService employeeActivityService = new EmployeeActivityServiceImpl(employeeActivityRepository);

    final EmployeeManagerView employeeManagerView = new EmployeeManagerView();
    final UserInfoService userInfoService = new UserInfoServiceImpl(userRepository);

    final UserValidator userValidator = new UserValidator(userRepository);
    final DateValidator dateValidator = new DateValidator();

    final ClientValidator clientValidator = new ClientValidator();

    final EmployeeManagerController employeeManagerController = new EmployeeManagerController(employeeManagerView,
            userInfoService, employeeActivityService, rightsRolesRepository, authenticationService, userValidator, dateValidator);

    final EmployeeController employeeController = new EmployeeController(employeeView, clientService, accountService,
            employeeActivityService, rightsRolesRepository, clientValidator);

    final AdminController adminController = new AdminController(adminView, employeeController, employeeManagerController);

    new LoginController(loginView, authenticationService, adminController, employeeController);
  }
}
