import controller.AccountController;
import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RolesRepository;
import repository.security.RolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AccountView;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RolesRepository rolesRepository = new RolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);


        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rolesRepository);

        final LoginView loginView = new LoginView();
        final AccountView accountView = new AccountView();
        final EmployeeView employeeView = new EmployeeView();
        final AdminView adminView = new AdminView();



        final UserValidator userValidator = new UserValidator(userRepository);


        new LoginController(loginView, authenticationService, userValidator,rolesRepository,userRepository
                ,clientRepository,accountRepository,employeeView,adminView);
        new EmployeeController(employeeView,clientRepository,accountRepository,accountView);
        new AccountController(accountView, accountRepository, clientRepository);
        new AdminController(adminView,userRepository,userValidator,authenticationService);
    }
}