package launcher;

import controller.LoginController;
import controller.admin.AdminController;
import controller.employee.AccountController;
import controller.employee.EmployeeController;
import database.DBConnectionFactory;
import repositories.account.AccountRepository;
import repositories.account.AccountRepositoryMySQL;
import repositories.client.ClientRepository;
import repositories.client.ClientRepositoryMySQL;
import repositories.security.RightsRolesRepository;
import repositories.security.RightsRolesRepositoryMySQL;
import repositories.user.UserRepository;
import repositories.user.UserRepositoryMySQL;
import services.account.AccountService;
import services.account.AccountServiceImplementation;
import services.admin.AdminService;
import services.admin.AdminServiceImplementation;
import services.client.ClientService;
import services.client.ClientServiceImplementation;
import services.user.AuthenticationService;
import services.user.AuthenticationServiceMySQL;
import view.LoginView;
import view.admin.AdminView;
import view.employee.AccountView;
import view.employee.EmployeeView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final AccountView accountView;

    private final LoginController loginController;
    private final AdminController adminController;
    private final EmployeeController employeeController;
    private final AccountController accountController;

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final AdminService adminService;
    private final AccountService accountService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

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
        this.clientRepository = new ClientRepositoryMySQL(connection, this.getClientRepository());
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.accountRepository = new AccountRepositoryMySQL(connection, this.getAccountRepository());
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientService = new ClientServiceImplementation(this.getClientRepository());
        this.adminService = new AdminServiceImplementation(this.authenticationService, this.userRepository);
        this.accountService = new AccountServiceImplementation(this.accountRepository);

        this.loginView = new LoginView();
        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();
        this.accountView = new AccountView();
        this.accountController = new AccountController(accountView, accountService);
        this.loginController = new LoginController(loginView, authenticationService);
        this.adminController = new AdminController(adminView, authenticationService, adminService);
        this.employeeController = new EmployeeController(employeeView, clientService);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public AccountService getAccountService(){
        return accountService;
    }

    public ClientService getClientService(){
        return clientService;
    }

    public AdminService getAdminService(){
        return adminService;
    }

    public AccountRepository getAccountRepository(){
        return accountRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ClientRepository getClientRepository(){return clientRepository;}

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public AccountView getAccountView(){
        return accountView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public AdminView getAdminView(){
        return adminView;
    }

    public EmployeeView getEmployeeView(){
        return employeeView;
    }

    public AccountController getAccountController(){
        return accountController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AdminController getAdminController(){
        return adminController;
    }

    public EmployeeController getEmployeeController(){
        return employeeController;
    }
}
