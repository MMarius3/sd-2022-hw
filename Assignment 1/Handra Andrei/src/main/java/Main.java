import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.LoginValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.*;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {

    public static void main(String[] args){
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);


        final CurrentSession currentSession = new CurrentSession();
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository,currentSession);

        final EmployeeService employeeService = new EmployeeServiceMySQL(clientRepository, accountRepository, rightsRolesRepository,currentSession);
        final AdminService adminService = new AdminServiceMySQL(userRepository,rightsRolesRepository);

        final LoginView loginView = new LoginView();
        final EmployeeView employeeView = new EmployeeView();
        final AdminView adminView = new AdminView();

        final UserValidator userValidator = new UserValidator(userRepository);
        final LoginValidator loginValidator = new LoginValidator(userRepository);
        final ClientValidator clientValidator = new ClientValidator(clientRepository);
        final AccountValidator accountValidator = new AccountValidator(accountRepository);

        new LoginController(loginView,employeeView,adminView, authenticationService, userValidator, loginValidator);
        new EmployeeController(employeeView,employeeService, clientValidator, accountValidator);
        new AdminController(adminView,adminService, userValidator);
    }
}
