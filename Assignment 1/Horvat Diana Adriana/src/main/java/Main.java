import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.Sentinel;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import respository.account.AccountRepository;
import respository.account.AccountRepositoryMySQL;
import respository.activity.ActivityRepository;
import respository.activity.ActivityRepositoryMySQL;
import respository.client.ClientRepository;
import respository.client.ClientRepositoryMySQL;
import respository.security.RolesRepository;
import respository.security.RolesRepositoryMySQL;
import respository.user.UserRepository;
import respository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.LoginView;
import view.admin.AdminAddEmployeeView;
import view.admin.AdminIndexView;
import view.admin.AdminUpdateEmployeeView;
import view.employee.*;

import java.sql.Connection;

public class Main {
    private static final String PRODUCTION = "bank app";

    public static void main(String[] args){
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RolesRepository rolesRepository = new RolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rolesRepository);

        final LoginView loginView = new LoginView();

        final UserValidator userValidator = new UserValidator(userRepository);
        final ClientValidator clientValidator = new ClientValidator(clientRepository);


        final ClientService clientService = new ClientServiceMySQL(clientRepository);

        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        final AccountService accountService = new AccountServiceMySQL(accountRepository);
        final AccountValidator accountValidator = new AccountValidator(clientRepository);
        final EmployeeIndexView employeeIndexView = new EmployeeIndexView();
        final EmployeeAddClientView employeeAddClientView = new EmployeeAddClientView();
        final EmployeeUpdateClientView employeeUpdateClientView = new EmployeeUpdateClientView();
        final EmployeeAddAccountView employeeAddAccountView = new EmployeeAddAccountView();
        final EmployeeUpdateAccountView employeeUpdateAccountView = new EmployeeUpdateAccountView();
        final EmployeeTransferMoneyView employeeTransferMoneyView = new EmployeeTransferMoneyView();
        final EmployeeProcessBillsView employeeProcessBillsView = new EmployeeProcessBillsView();

        final ActivityRepository activityRepository = new ActivityRepositoryMySQL(connection);
        final ActivityService activityService = new ActivityServiceMySQL(activityRepository);
        EmployeeController employeeController = new EmployeeController(clientService, clientValidator, employeeIndexView, employeeAddClientView,
                employeeUpdateClientView, employeeAddAccountView, accountValidator, accountService, employeeUpdateAccountView,
//                employeeTransferMoneyView, employeeProcessBillsView, activityService, user);
                employeeTransferMoneyView, employeeProcessBillsView, activityService, authenticationService);



        final UserService userService = new UserServiceMySQL(userRepository);
        final AdminIndexView adminIndexView = new AdminIndexView();
        final AdminAddEmployeeView adminAddEmployeeView = new AdminAddEmployeeView();
        final AdminUpdateEmployeeView adminUpdateEmployeeView = new AdminUpdateEmployeeView();
        AdminController adminController = new AdminController(userService, authenticationService ,userValidator, adminIndexView,
                adminAddEmployeeView, adminUpdateEmployeeView, activityService);

        new LoginController(adminIndexView, employeeIndexView, loginView, authenticationService, userValidator, connection);
    }
}
