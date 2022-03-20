package launcher;

import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientAccountRepository;
import repository.client.ClientAccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.CRUDAccount;
import service.account.CRUDAccountMySQL;
import service.authentication.AuthenticationService;
import service.authentication.AuthenticationServiceMySQL;
import service.client.CRUClient;
import service.client.CRUClientMySQL;
import view.*;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdminView adminView;
    private final AddClientView addClientView;
    private final UpdateClientView updateClientView;
    private final DisplayClientView displayClientView;
    private final AddAccountView addAccountView;
    private final UpdateAccountView updateAccountView;

    private final LoginController loginController;
    private final EmployeeController employeeController;
    private final AdminController adminController;

    private final AuthenticationService authenticationService;
    private final CRUClient cruClient;
    private final CRUDAccount crudAccount;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientAccountRepository clientAccountRepository;

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
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.clientAccountRepository = new ClientAccountRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.cruClient = new CRUClientMySQL(clientRepository);
        this.crudAccount = new CRUDAccountMySQL(clientRepository, accountRepository, clientAccountRepository);
        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.adminView = new AdminView();
        this.addClientView = new AddClientView();
        this.addAccountView = new AddAccountView();
        this.updateAccountView = new UpdateAccountView();
        this.updateClientView = new UpdateClientView();
        this.displayClientView = new DisplayClientView();
        this.loginController = new LoginController(this, loginView, authenticationService);
        this.employeeController = new EmployeeController(employeeView, addClientView, updateClientView, displayClientView, addAccountView, updateAccountView, cruClient, crudAccount);
        this.adminController = new AdminController();
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

    public AdminView getAdminView() {
        return adminView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
