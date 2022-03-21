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
import service.user.CRUDUser;
import service.user.CRUDUserMySQL;
import view.*;
import view.admin.*;
import view.employee.*;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;
    private final EmployeeView employeeView;
    private final AdminView adminView;

    private final LoginController loginController;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

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
        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        ClientAccountRepository clientAccountRepository = new ClientAccountRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        CRUClient cruClient = new CRUClientMySQL(clientRepository);
        CRUDAccount crudAccount = new CRUDAccountMySQL(clientRepository, accountRepository, clientAccountRepository);
        CRUDUser crudUser = new CRUDUserMySQL(userRepository, authenticationService, rightsRolesRepository);
        this.loginView = new LoginView();
        this.employeeView = new EmployeeView();
        this.adminView = new AdminView();
        AddClientView addClientView = new AddClientView();
        AddAccountView addAccountView = new AddAccountView();
        UpdateAccountView updateAccountView = new UpdateAccountView();
        DeleteAccountView deleteAccountView = new DeleteAccountView();
        UpdateClientView updateClientView = new UpdateClientView();
        DisplayClientView displayClientView = new DisplayClientView();
        DisplayAccountView displayAccountView = new DisplayAccountView();
        SearchClientAccountView searchClientAccountView = new SearchClientAccountView();
        TransferMoneyView transferMoneyView = new TransferMoneyView();
        AddUserView addUserView = new AddUserView();
        EditUserView editUserView = new EditUserView();
        DeleteUserView deleteUserView = new DeleteUserView();
        DisplayUserView displayUserView = new DisplayUserView();
        ProcessUtilitiesBillsView processUtilitiesBillsView = new ProcessUtilitiesBillsView();
        this.loginController = new LoginController(this, loginView, authenticationService);
        EmployeeController employeeController = new EmployeeController(employeeView, addClientView, updateClientView, displayClientView,
                addAccountView, updateAccountView, deleteAccountView, displayAccountView, searchClientAccountView, transferMoneyView, processUtilitiesBillsView, cruClient, crudAccount);
        AdminController adminController = new AdminController(adminView, addUserView, editUserView, deleteUserView, displayUserView, crudUser);
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
