package launcher;

import controller.*;
import database.DatabaseConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.activity.ActivityService;
import service.activity.ActivityServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.*;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;

    private final EmployeeView employeeView;
    private final EmployeeController employeeController;

    private final AdminView adminView;
    private final AdminController adminController;

    private final TransferView transferView;
    private final TransferController transferController;

    private final BillView billView;
    private final BillController billController;

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final ActivityService activityService;
    private final AccountService accountService;
    private final UserService userService;

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ActivityRepository activityRepository;
    private final AccountRepository accountRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private final TableProcessing tableProcessing;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests, TableProcessing tableProcessing) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests, tableProcessing);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests, TableProcessing tableProcessing) {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.tableProcessing = tableProcessing;
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.activityRepository = new ActivityRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientService = new ClientServiceImpl(clientRepository);
        this.activityService = new ActivityServiceImpl(activityRepository, this.rightsRolesRepository);
        this.accountService = new AccountServiceImpl(accountRepository);
        this.userService = new UserServiceImpl(userRepository,rightsRolesRepository);
        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.adminView = new AdminView();
        this.transferView = new TransferView();
        this.billView = new BillView();
        this.loginController = new LoginController(loginView, employeeView, adminView, authenticationService);
        this.adminController = new AdminController(adminView, loginView, userService, activityService, tableProcessing);
        this.transferController = new TransferController(transferView, employeeView, accountService, clientService,
                activityService, tableProcessing);
        this.billController = new BillController(billView,employeeView,accountService,clientService,activityService,
                tableProcessing);
        this.employeeController = new EmployeeController(employeeView, loginView, transferView, billView,clientService,
                activityService, accountService, tableProcessing);
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

    public LoginController getLoginController() {
        return loginController;
    }
}
