package launcher;

import controller.EmployeeController;
import controller.LoginController;
import database.DataBaseConnectionFactory;
import repository.Account.AccountRepositoryMySQL;
import repository.Client.ClientRepositoryMySQL;
import repository.Security.SecurityRepository;
import repository.Security.SecurityRepositoryMySQL;
import repository.User.UserRepository;
import repository.User.UserRepositoryMySQL;
import service.Account.AccountService;
import service.Account.AccountServiceImpl;
import service.Client.ClientService;
import service.Client.ClientServiceImpl;
import service.Service.UserService;
import service.Service.UserServiceImpl;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final EmployeeView employeeView;


    private final LoginController loginController;
    private final EmployeeController employeeController;


    private final UserService userService;
    private final ClientService clientService;
    private final AccountService accountService;

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final ClientRepositoryMySQL clientRepositoryMySQL;
    private final AccountRepositoryMySQL accountRepositoryMySQL;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DataBaseConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.securityRepository = new SecurityRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, securityRepository);
        this.clientRepositoryMySQL = new ClientRepositoryMySQL(connection);
        this.accountRepositoryMySQL = new AccountRepositoryMySQL(connection);
        this.clientService = new ClientServiceImpl(clientRepositoryMySQL);
        this.accountService = new AccountServiceImpl(accountRepositoryMySQL);
        this.userService = new UserServiceImpl(this.userRepository, this.securityRepository);
        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.employeeController = new EmployeeController(this.employeeView, this.accountService, this.clientService);
        this.loginController = new LoginController(loginView, userService, employeeController);

    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public UserService getUserService() {
        return userService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SecurityRepository getSecurityRepository() {
        return securityRepository;
    }

    public ClientRepositoryMySQL getClientRepositoryMySQL() {
        return clientRepositoryMySQL;
    }

    public AccountRepositoryMySQL getAccountRepositoryMySQL() {
        return accountRepositoryMySQL;
    }

    public static ComponentFactory getInstance() {
        return instance;
    }

    public static void setInstance(ComponentFactory instance) {
        ComponentFactory.instance = instance;
    }
}