package launcher;

import controller.AdminViewController;
import controller.LoginController;
import controller.UserViewController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryPostgres;
import repository.logger.LoggerRepository;
import repository.logger.LoggerRepositoryPostgres;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryPostgres;
import repository.user.UserRepository;
import repository.user.UserRepositoryPostgres;
import service.banking.AccountService;
import service.banking.AccountServiceImpl;
import service.logger.LoggerService;
import service.logger.LoggerServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServicePostgres;
import service.user.ClientService;
import service.user.ClientServicePostgres;
import view.AdminView;
import view.LoginViewNew;
import view.UserView;

import java.sql.Connection;

public class ComponentFactory {

  private final LoginViewNew loginView;
  private final UserView userView;
  private final AdminView adminView;

  private final LoginController loginController;
  private final UserViewController userViewController;
  private final AdminViewController adminViewController;

  private final AuthenticationService authenticationService;
  private final ClientService clientService;
  private final AccountService accountService;
  private final LoggerService loggerService;

  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;
  private final AccountRepository accountRepository;
  private final LoggerRepository loggerRepository;


  private static ComponentFactory instance;

  public static ComponentFactory instance(Boolean componentsForTests) {
    if (instance == null) {
      instance = new ComponentFactory(componentsForTests);
    }
    return instance;
  }

  private ComponentFactory(Boolean componentsForTests) {
    Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
    this.rightsRolesRepository = new RightsRolesRepositoryPostgres(connection);
    this.userRepository = new UserRepositoryPostgres(connection, rightsRolesRepository);
    this.accountRepository = new AccountRepositoryPostgres(connection, userRepository);
    this.loggerRepository = new LoggerRepositoryPostgres(connection, userRepository);


    this.authenticationService = new AuthenticationServicePostgres(this.userRepository, this.rightsRolesRepository);
    this.clientService = new ClientServicePostgres(this.userRepository);
    this.accountService = new AccountServiceImpl(accountRepository);
    this.loggerService = new LoggerServiceImpl(loggerRepository);

    this.loginView = new LoginViewNew("Authentication");
    this.loginController = new LoginController(loginView, authenticationService, loggerService, this);

    this.userView = new UserView("Employee Menu");
    this.userViewController = new UserViewController(userView, clientService, accountService,
            loggerService);

    this.adminView = new AdminView("Administrator Menu");
    this.adminViewController = new AdminViewController(adminView, clientService, loggerService);
//    bookRepositoryMySQL = new BookRepositoryMySQL(connection);
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

  public LoginViewNew getLoginView() {
    return loginView;
  }

  public UserView getUserView() { return userView; }

  public AdminView getAdminView() { return adminView; }

//  public BookRepositoryMySQL getBookRepositoryMySQL() {
//    return bookRepositoryMySQL;
//  }

  public LoginController getLoginController() {
    return loginController;
  }

  public UserViewController getUserViewController() {
    return userViewController;
  }

  public AdminViewController getAdminViewController() {
    return adminViewController;
  }
}
