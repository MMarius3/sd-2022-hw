import controller.*;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.type.AccountTypeRepository;
import repository.type.AccountTypeRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.LoginView;
import view.MainUI;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main{


    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final UserService userService = new UserServiceImpl(userRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);

        final UserValidator userValidator = new UserValidator(userRepository);


        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final ClientService clientService = new ClientServiceImpl(clientRepository);

        final AccountTypeRepository accountTypeRepository = new AccountTypeRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(accountTypeRepository, connection);
        final AccountService accountService = new AccountServiceImpl(accountRepository);



        final AccountController accountController = new AccountController(accountService, accountRepository);
        final EmployeeController employeeController = new EmployeeController(userService);
        final ClientController clientController = new ClientController(clientService, accountService, accountController);
        final UserController userController = new UserController(userService, clientService, clientController, employeeController);
        final LoginController loginController = new LoginController(authenticationService, userValidator, userController);

        LoginView.setController(loginController);

        MainUI.launchGUI();
        //loginView.launchGUI();



         //launch();
    }


    /*public void start(Stage window)  {
        window.setTitle("CNC flame cutting machine simulator");

        LoginView loginView = new LoginView();
        window.setScene(loginView.display(window));
        window.show();

    }*/
}
