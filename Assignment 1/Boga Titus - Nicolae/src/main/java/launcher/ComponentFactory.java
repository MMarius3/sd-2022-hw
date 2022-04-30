package launcher;

import controller.AdminController;
import controller.LoginController;
import controller.RegularUserController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;
import service.transfer.TransferService;
import service.transfer.TransferServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.LoginView;
import view.RegularUserView;

import java.sql.Connection;

public class ComponentFactory {

  private final RegularUserView regularUserView;
  private final LoginView loginView;
  private final AdminView adminView;

  private final LoginController loginController;
  private final RegularUserController regularUserController;
  private final AdminController adminController;

  private final AuthenticationService authenticationService;
  private final ClientService clientService;
  private final AccountService accountService;
  private final TransferService transferService;
  private final EmployeeService employeeService;

  private final ClientRepository clientRepository;
  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;
  private final ClientRepositoryMySQL bookRepositoryMySQL;

  private static ComponentFactory instance;

  public static ComponentFactory instance(Boolean componentsForTests) {
    if (instance == null) {
      instance = new ComponentFactory(componentsForTests);
    }
    return instance;
  }

  private ComponentFactory(Boolean componentsForTests) {
    Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
    this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

    this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    this.accountRepository = new AccountRepositoryMySQL(connection);
    this.clientRepository = new ClientRepositoryMySQL(connection);
    this.transferService = new TransferServiceImpl(this.accountRepository);
    this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
    this.clientService = new ClientServiceImpl(this.clientRepository);
    this.accountService = new AccountServiceImpl(this.accountRepository);
    this.employeeService= new EmployeeServiceImpl(this.userRepository);
    this.loginView = new LoginView();
    this.adminView = new AdminView();
    this.regularUserView = new RegularUserView();
    this.adminController = new AdminController(adminView,employeeService);
    this.loginController = new LoginController(loginView, authenticationService);
    this.regularUserController = new RegularUserController(regularUserView, clientService,accountService,transferService);

    bookRepositoryMySQL = new ClientRepositoryMySQL(connection);
  }

  public AdminView getAdminView() {
    return adminView;
  }

  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public RightsRolesRepository getRightsRolesRepository() {
    return rightsRolesRepository;
  }

  public LoginView getLoginView() {
    return loginView;
  }

  public ClientRepositoryMySQL getBookRepositoryMySQL() {
    return bookRepositoryMySQL;
  }

  public LoginController getLoginController() {
    return loginController;
  }

  public RegularUserView getRegularUserView() {
    return regularUserView;
  }
}
