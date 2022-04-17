package launcher;

import controller.admin.AdminController;
import controller.employee.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.EmployeeActivityRepository;
import repository.activity.EmployeeActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.activity.EmployeeActivityService;
import service.activity.EmployeeActivityServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.admin.AdminView;
import view.employee.EmployeeView;
import view.login.LoginView;

import java.sql.Connection;

public class ComponentFactory {
  private final LoginView loginView;
  private final EmployeeView employeeView;
  private final AdminView adminView;

  private final LoginController loginController;
  private final EmployeeController employeeController;
  private final AdminController adminController;

  private final AuthenticationService authenticationService;
  private final ClientService clientService;
  private final AccountService accountService;
  private final EmployeeActivityService employeeActivityService;
  private final UserService userService;

  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;
  private final ClientRepository clientRepository;
  private final AccountRepository accountRepository;
  private final EmployeeActivityRepository employeeActivityRepository;

  private static ComponentFactory instance;

  public static ComponentFactory instance(Boolean componentsForTests) {
    if(instance == null) {
      instance = new ComponentFactory(componentsForTests);
    }
    return instance;
  }

  private ComponentFactory(Boolean componentsForTests) {
    Connection connection = DBConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
    this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    this.clientRepository = new ClientRepositoryMySQL(connection);
    this.accountRepository = new AccountRepositoryMySQL(connection, clientRepository);
    this.employeeActivityRepository = new EmployeeActivityRepositoryMySQL(connection, userRepository, rightsRolesRepository);
    this.authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    this.employeeActivityService = new EmployeeActivityServiceImpl(employeeActivityRepository);
    this.userService = new UserServiceMySQL(userRepository);
    this.loginView = new LoginView();
    this.employeeView = new EmployeeView();
    this.adminView = new AdminView();
    this.clientService = new ClientServiceImpl(clientRepository);
    this.accountService = new AccountServiceImpl(accountRepository);
    this.adminController = new AdminController(adminView, userService, employeeActivityService);
    this.loginController = new LoginController(loginView, adminView, employeeView, authenticationService, rightsRolesRepository);
    this.employeeController = new EmployeeController(employeeView, clientService, accountService, employeeActivityService, rightsRolesRepository, loginController);

  }

  public RightsRolesRepository getRightsRolesRepository() {
    return rightsRolesRepository;
  }

  public LoginView getLoginView() {
    return loginView;
  }

  public LoginController getLoginController() {
    return loginController;
  }

  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }
}
