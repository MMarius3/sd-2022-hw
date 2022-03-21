package launcher;

import controller.LoginController;
import controller.UserController;
import database.DBConnectionFactory;
import model.User;
import repository.book.BookRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.LoginView;
import view.UserView;

import java.sql.Connection;

public class ComponentFactory {

  private final LoginView loginView;

  private final LoginController loginController;

//  private final UserView userView;
//  private final UserController userController;

  private final AuthenticationService authenticationService;
  private final ClientService clientService;
  private final UserService userService;

  private final UserRepository userRepository;
  private final RightsRolesRepository rightsRolesRepository;
  private final BookRepositoryMySQL bookRepositoryMySQL;
  private final ClientRepositoryMySQL clientRepositoryMySQL;
  private final UserRepositoryMySQL userRepositoryMySQL;

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
    this.clientRepositoryMySQL = new ClientRepositoryMySQL(connection);
    this.userRepositoryMySQL = new UserRepositoryMySQL(connection,rightsRolesRepository);
    this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
    this.clientService = new ClientServiceImpl(this.clientRepositoryMySQL);
    this.userService = new UserServiceImpl(this.userRepositoryMySQL);
    this.loginView = new LoginView();
    this.loginController = new LoginController(loginView, authenticationService, clientService, userService);
    bookRepositoryMySQL = new BookRepositoryMySQL(connection);
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
//  public UserView getUserView(){
//    return userView;
//  }

  public BookRepositoryMySQL getBookRepositoryMySQL() {
    return bookRepositoryMySQL;
  }

  public LoginController getLoginController() {
    return loginController;
  }

//  public UserController getUserController(){
//    return userController;
//  }

}
